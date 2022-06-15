package com.notarmaso.beeritup.db.repositories


import com.notarmaso.beeritup.models.*
import com.notarmaso.db_access_setup.dal.DBInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class UserRepository(val dbInstance: DBInstance) : IUserRepository {

    override suspend fun getUsers(): Response<MutableList<UserRecieve>> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.getUsers()

        }
        response.await()
    }

    override suspend fun getUser(id: Int): Response<UserRecieve> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.getUser(id)

        }
        response.await()
    }

    override suspend fun isEmailAvailable(email: String): Response<Boolean> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.isEmailAvailable(email)
        }
        response.await()
    }


    override suspend fun addUser(userToPost: UserToPost): Response<UserRecieve> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.addUser(userToPost)
            }

            response.await()
        }


    override suspend fun updateUser(id: String, user: UserToPost): Response<String> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.updateUser(user.name, user)
            }
            response.await()
        }

    override suspend fun deleteUser(id: String): Response<String> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.deleteUser(id)
        }
        response.await()
    }

    override suspend fun login(userLoginObject: UserLoginObject): Response<UserRecieve> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.login(userLoginObject)
            }
            response.await()
        }

    override suspend fun isAssigned(userId: Int): Response<UserLoginStatus> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.isAssigned(userId)
        }
        response.await()
    }

    override suspend fun userOwed(userId: Int): Response<List<UserPaymentObject>> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.userOwed(userId)
        }
        response.await()
    }

    override suspend fun userOwes(userId: Int): Response<List<UserPaymentObject>> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.userOwes(userId)
        }
        response.await()
    }

    override suspend fun makePayment(uId: Int, ownerId: Int): Response<String> = coroutineScope {
        val response = async(Dispatchers.IO) {
            dbInstance.userApi.makePayment(uId, ownerId)
        }
        response.await()
    }

    override suspend fun paidLogbook(uId: Int): Response<List<BeverageLogEntryObj>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.paidLogbook(uId)
            }
            response.await()
        }

    override suspend fun soldLogbook(uId: Int): Response<List<BeverageLogEntryObj>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.soldLogbook(uId)
            }
            response.await()
        }

    override suspend fun addedLogbook(uId: Int): Response<List<BeverageLogEntryObj>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.addedLogbook(uId)
            }
            response.await()
        }

    override suspend fun consumedLogbook(uId: Int): Response<List<BeverageLogEntryObj>> =
        coroutineScope {
            val response = async(Dispatchers.IO) {
                dbInstance.userApi.consumedLogbook(uId)
            }
            response.await()
        }
}