package com.notarmaso.beeritupcompose.views.addSelectBeer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notarmaso.beeritupcompose.*
import com.notarmaso.beeritupcompose.db.repositories.BeerRepository
import com.notarmaso.beeritupcompose.db.repositories.UserRepository
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction
import com.notarmaso.beeritupcompose.models.Beer
import com.notarmaso.beeritupcompose.models.BeerGroup
import com.notarmaso.beeritupcompose.models.GlobalBeer
import com.notarmaso.beeritupcompose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


class BeerQuantityViewModel(val service: Service, val beerService: BeerService): ViewModel(), ViewModelFunction {


    /* Ting jeg mangler at implementere:
       *
       * Hvis jeg A, skylder B penge, så hvis B køber af mig, fratrukkes prisen hvad jeg skylder B
       *
   */

    var qtySelected by mutableStateOf(1)
    var pricePaid by mutableStateOf("120")
    var pricePerBeer: Float = 0f
    var beerCount by mutableStateOf(0)
    private var mapOfBeer: MutableList<Beer>? = null

    private var _beerList = mutableStateListOf<Beer>()
    val beerList: List<Beer> get() = _beerList

    private val beerRepository: BeerRepository = BeerRepository(service.context)
    private val userRepository: UserRepository = UserRepository(service.context)

    init {
        beerService.beerObs.register(this)
        service.miscObs.register(this)
    }

    override fun navigate(location: String){
        service.navigate(location)
    }
    override fun navigateBack(location: String){
        service.navigateBack(location)
    }

    override fun update() {

        qtySelected = if (service.currentPage == MainActivity.ADD_BEER) 24 else 1

        if(service.currentPage != MainActivity.ADD_BEER) {
            _beerList.clear()
            mapOfBeer = beerService.mapOfBeer[service.selectedGlobalBeer?.name]
            if (mapOfBeer != null) {

                for (x in mapOfBeer!!) {
                    _beerList.add(x)
                }
            }
        }
    }


    fun incrementCounter(){

        if(service.currentPage == MainActivity.SELECT_BEER){
            if(qtySelected >= beerCount) qtySelected = beerCount
            else qtySelected++
        } else {
            if(qtySelected >= 24) qtySelected = 24
            else qtySelected++
        }
    }

    fun decrementCounter(){
        if(qtySelected <= 1) qtySelected = 1
        else qtySelected--
    }

    /*This section is a mess, fix it up later, it calculates the price of beer */
    fun onConfirm(isAddingBeer: Boolean = false){

        if(isAddingBeer) {
            val calcPrice = pricePaid.toFloat() / qtySelected.toFloat()
            val priceRounded = BigDecimal(calcPrice.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
            this.pricePerBeer = priceRounded.toFloat()

            service.createAlertBoxAddBeer(pricePerBeer, ::onAccept, qtySelected)
            return
        }

        val mapOfBeer = beerService.mapOfBeer[service.selectedGlobalBeer?.name]
        var priceToPay = 0f
        for (i in 0 until qtySelected) {
            priceToPay += mapOfBeer?.get(i)!!.price
        }

        service.createAlertBoxSelectBeer(qtySelected, price =  priceToPay, ::onAccept)

    }

    private fun onAccept() {
        val selectedBeer = service.selectedGlobalBeer
        val mapOfBeer = beerService.mapOfBeer[selectedBeer?.name]

        if(selectedBeer != null) {
            when(service.currentPage){
                MainActivity.ADD_BEER -> mapOfBeer?.let { handleAddBeer(selectedBeer, it) }
                MainActivity.SELECT_BEER -> mapOfBeer?.let { handleSelectBeer(selectedBeer, it) }
            }
        }


    }

    private fun handleAddBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>,
    ) {
        val user = service.currentUser.name


        viewModelScope.launch(Dispatchers.IO) {
            //val priceRounded = BigDecimal(pricePerBeer!!.toDouble()).setScale(1, RoundingMode.HALF_EVEN)
            for (i in 0 until qtySelected) {


                    val beer = Beer(name = selectedBeer.name,
                        price = pricePerBeer,
                        owner = user)
                    mapOfBeer.add(beer)

            }

            /* Update BeerGroup  STRING STRING */
            val beerGroup = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))

            beerRepository.updateBeerGroup(beerGroup)
           // service.db.beerDao().updateBeerGroup(beerGroup)
            service.userObs.notifySubscribers()
        }
    }


    private fun handleSelectBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>

    ) {
        service.getDate()

        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = service.currentUser

            val userOwesToList = currentUser.owesTo.fromJsonToListFloat()


            var beer: Beer?
            var price: Float?
            val prevBeerName: String = mapOfBeer[0].owner

            /* HER */
            var beerOwner: User =  userRepository.getUser(prevBeerName)
            var prevBeerOwner: String? = beerOwner.name

            val totalBeers: MutableMap<String, Int> = currentUser.totalBeers.fromJsonToListInt()
            val month = service.currentDate
            var beersToAdd: Int  = 0
            for (i in 0 until qtySelected) {

                /*Increment total beers*/

                beer = mapOfBeer.removeFirstOrNull()

                beersToAdd++
                if (beer != null && currentUser.name != beer.owner) {

                    if(prevBeerOwner != beer.owner) beerOwner = userRepository.getUser(beer.owner)


                    price = beer.price

                    val beerOwnerOwedFromList = beerOwner.owedFrom.fromJsonToListFloat()

                    setPayments(userOwesToList, beerOwner, price)
                    setPayments(beerOwnerOwedFromList, currentUser, price)

                    beerOwner.owedFrom = beerOwnerOwedFromList.fromListFloatToJson()


                    userRepository.updateUser(beerOwner)
                    if (prevBeerOwner != beer.owner) prevBeerOwner = beer.owner

                }
            }
            val count = beersToAdd
            val prevValue = totalBeers[month]
            val prevTotalValue = totalBeers["TOTAL"]

            if (prevValue != null) totalBeers[month] = prevValue + count
            if (prevTotalValue != null) totalBeers["TOTAL"] = prevTotalValue + count

            currentUser.totalBeers = totalBeers.fromListIntToJson()
            currentUser.owesTo = userOwesToList.fromListFloatToJson()


            userRepository.updateUser(currentUser)


            val beerGroupToUpdate = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))


            beerRepository.updateBeerGroup(beerGroupToUpdate)
            service.miscObs.notifySubscribers()

        }

    }




    private fun setPayments(
        paymentList: MutableMap<String, Float>,
        customerToUpdate: User?,
        priceToUpdate: Float,
    ) {
        if (!paymentList.containsKey(customerToUpdate?.name)) customerToUpdate?.name?.let { paymentList.put(it, priceToUpdate) }
        else {
            customerToUpdate?.name?.let {
                val prevPrice = paymentList[it]

                if (prevPrice != null) {
                    paymentList[it] = prevPrice + priceToUpdate
                }
            }
        }
    }

    fun setTotalQty(selectedBeer: String) {
        beerCount = beerService.getStock(selectedBeer)
    }


}
















