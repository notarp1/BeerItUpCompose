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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import java.math.BigDecimal
import java.math.RoundingMode


class BeerQuantityViewModel(val service: Service, private val beerService: BeerService): ViewModel(), ViewModelFunction {


    /* Ting jeg mangler at implementere:
       *
       * Hvis jeg A, skylder B penge, så hvis B køber af mig, fratrukkes prisen hvad jeg skylder B
       *
   */

    var qtySelected by mutableStateOf(1)
    var pricePaid by mutableStateOf("120")
    var beerCount by mutableStateOf(0)
    private var pricePerBeer: Float = 0f
    private var mapOfBeer: MutableList<Beer>? = null

    private var _beerList = mutableStateListOf<Beer>()
    val beerList: List<Beer> get() = _beerList

    private val beerRepository: BeerRepository = BeerRepository(service.context)
    private val userRepository: UserRepository = UserRepository(service.context)

    init {
        service.observer.register(this)
    }



    fun incrementCounter(){

        if(service.currentPage == Pages.BUY_BEER){
            if(qtySelected >= beerCount){
                qtySelected = beerCount
                return
            }
            if(qtySelected >= 10 ){
                qtySelected = 10
                return
            }else qtySelected++

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


        val mapOfBeer = beerService.mapOfBeer[service.selectedGlobalBeer.name]
        var priceToPay = 0f
        for (i in 0 until qtySelected) {
            priceToPay += mapOfBeer?.get(i)!!.price
        }
        val priceRounded = BigDecimal(priceToPay.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        service.createAlertBoxSelectBeer(qtySelected, price =  priceRounded.toFloat(), ::onAccept)

    }

    private fun onAccept() {
        val selectedBeer = service.selectedGlobalBeer
        val mapOfBeer = beerService.mapOfBeer[selectedBeer.name]

        when (service.currentPage) {
            Pages.ADD_BEER -> mapOfBeer?.let { handleAddBeer(selectedBeer, it) }
            Pages.BUY_BEER -> mapOfBeer?.let { handleSelectBeer(selectedBeer, it) }
            else -> {}
        }





    }

    private fun handleAddBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>,
    ) {
        val currentUser= service.currentUser

        /*Log update*/
        val date = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val log = currentUser.beersAddedLog.fromJsonToList()
        log.add("You added $qtySelected ${selectedBeer.name} at $date")
        service.currentUser.beersAddedLog = log.fromListToJson()


        viewModelScope.launch(Dispatchers.IO) {
            //val priceRounded = BigDecimal(pricePerBeer!!.toDouble()).setScale(1, RoundingMode.HALF_EVEN)
            for (i in 0 until qtySelected) {


                    val beer = Beer(name = selectedBeer.name,
                        price = pricePerBeer,
                        owner = currentUser.name)
                    mapOfBeer.add(beer)

            }

            /* Update BeerGroup  STRING STRING */
            val beerGroup = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))

            beerRepository.updateBeerGroup(beerGroup)
           // service.db.beerDao().updateBeerGroup(beerGroup)
            currentUser.totalAddedDkk += pricePaid.toFloat()

            userRepository.updateUser(user = currentUser)

            service.observer.notifySubscribers(Pages.SELECT_BEER_QUANTITY.value)

            viewModelScope.launch(Dispatchers.Main) {
                service.navigateBack(Pages.MAIN_MENU)
            }
        }
    }


    private fun handleSelectBeer(
        selectedBeer: GlobalBeer,
        mapOfBeer: MutableList<Beer>

    ) {
        service.getDateMonth()

        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = service.currentUser

            val userOwesToList = currentUser.owesTo.fromJsonToListFloat()


            var beer: Beer?
            val prevBeerName: String = mapOfBeer[0].owner

            /* HER */
            var beerOwner: User =  userRepository.getUser(prevBeerName)
            var prevBeerOwner: String? = beerOwner.name

            val totalBeers: MutableMap<String, Int> = currentUser.totalBeers.fromJsonToListInt()
            val month = service.currentDate

            var totalPaid: Float = 0f
            val date = Clock.System.todayAt(TimeZone.currentSystemDefault())

            var beersToAdd: Int  = 0
            for (i in 0 until qtySelected) {

                /*Increment total beers*/

                beer = mapOfBeer.removeFirstOrNull()

                beersToAdd++
                if (beer != null && currentUser.name != beer.owner) {

                    if(prevBeerOwner != beer.owner) beerOwner = userRepository.getUser(beer.owner)


                    totalPaid += beer.price

                    val beerOwnerOwedFromList = beerOwner.owedFrom.fromJsonToListFloat()

                    setPayments(userOwesToList, beerOwner, beer.price)
                    setPayments(beerOwnerOwedFromList, currentUser, beer.price)

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

            /*Update log*/
            val log = currentUser.beersBoughtLog.fromJsonToList()
            log.add("You bought $beersToAdd ${selectedBeer.name} at $date for $totalPaid")
            currentUser.beersBoughtLog = log.fromListToJson()


            currentUser.totalSpentDkk += totalPaid

            userRepository.updateUser(currentUser)


            val beerGroupToUpdate = BeerGroup(selectedBeer.name, serializeBeerGroup(mapOfBeer))

            service.latestUser = currentUser

            beerRepository.updateBeerGroup(beerGroupToUpdate)

            //Updatebeers
            service.observer.notifySubscribers(Pages.MAIN_MENU.value)
            viewModelScope.launch(Dispatchers.Main) { navigateBack(Pages.MAIN_MENU) }
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
                    val total: Float = prevPrice + priceToUpdate
                    val priceRounded = BigDecimal(total.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                    paymentList[it] = priceRounded.toFloat()
                }
            }
        }
    }

    fun setTotalQty(selectedBeer: String) {
        beerCount = beerService.getStock(selectedBeer)
    }



    private fun isAddingBeer(page: String): Boolean{
        return page == Pages.ADD_BEER.value
    }

    private fun isBuyingBeer(page: String): Boolean{
        return page == Pages.BUY_BEER.value
    }

    override fun update(page: String) {


        if (isAddingBeer(page))  qtySelected = 24 else if (isBuyingBeer(page)) qtySelected = 1


        if(isBuyingBeer(page)) {
            _beerList.clear()
            mapOfBeer = beerService.mapOfBeer[service.selectedGlobalBeer?.name]
            if (mapOfBeer != null) {

                for (x in mapOfBeer!!) {
                    _beerList.add(x)
                }
            }
        }
    }

    override fun navigate(location: Pages){
        service.navigate(location)
    }
    override fun navigateBack(location: Pages){
        service.navigateBack(location)
    }
}
















