package com.ymc.library_base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by ymc on 2020/9/25.
 * @Description
 */
abstract class BaseViewModel : ViewModel() {

    val loadingLiveData = SingleLiveData<Boolean>()

    val errorLiveData = SingleLiveData<Throwable>()

    /**
     * @deprecated
     */
    fun launch(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit,
        complete: suspend () -> Unit
    ) {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                error(e)
            } finally {
                complete()
            }
        }
    }

}