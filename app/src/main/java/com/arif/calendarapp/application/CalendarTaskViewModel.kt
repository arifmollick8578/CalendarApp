package com.arif.calendarapp.application

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arif.calendarapp.data.di.AppModule
import com.arif.calendarapp.data.remote.dto.TaskDetails
import com.arif.calendarapp.data.repositories.FakeRepository
import com.arif.calendarapp.domain.repositories.CalendarTaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CalendarTaskViewModel : ViewModel() {
    private val repository: CalendarTaskRepository = AppModule.provideCalendarTaskRepository()

    private val _calendarEvent = MutableLiveData<List<TaskDetails>>(emptyList())

    /** Live data to observe changes in [TaskDetails] list. */
    val calendarEvent: LiveData<List<TaskDetails>> = _calendarEvent

    init {
        fetchMockData()
        fetchDataFromAPI()
    }

    /** Add [event] to the day. */
    fun addDailyEvent(event: TaskDetails) {
        val existingEvent = _calendarEvent.value?.toMutableList()
        existingEvent?.add(event)
        _calendarEvent.postValue(existingEvent)
    }

    private fun fetchMockData() {
        // We can use [viewModelScope] also instead of CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            _calendarEvent.postValue(
                FakeRepository.getAllCalendarEvent()
            )
        }
    }

    private fun fetchDataFromAPI() {
        // We can use [viewModelScope] also instead of CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.getCalendarTaskLists(123).enqueue(
                    object : Callback<List<TaskDetails>> {
                        override fun onResponse(
                            call: Call<List<TaskDetails>>,
                            response: Response<List<TaskDetails>>
                        ) {
                            Log.d("TAG", "onResponse data from API call ${response.body()}")
                            // Updating event list with API data
                            _calendarEvent.postValue(
                                response.body()
                            )
                        }

                        override fun onFailure(call: Call<List<TaskDetails>>, t: Throwable) {
                            Log.d("TAG", "onFailure with throwable $t")
                        }

                    }
                )
            } catch (exception: Exception) {
                Log.d("TAG", "Exception to API call: $exception")
            }
        }
    }
}