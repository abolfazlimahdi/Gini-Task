package abolfazli.mahdi.ginitask.home.ui

import abolfazli.mahdi.ginitask.home.data.HomeRepository
import abolfazli.mahdi.ginitask.home.ui.entities.Invoice
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.gini.android.capture.network.model.GiniCaptureSpecificExtraction
import net.gini.android.core.api.models.SpecificExtraction
import java.sql.Timestamp
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val invoices = liveData(coroutineContext) {
        emitSource(homeRepository.getInvoicesLocal().asLiveData())
    }

    private val invoiceId = MutableLiveData<Long>()

    val invoiceDetail = invoiceId.switchMap {
        homeRepository.getInvoiceByIdLocal(it).asLiveData(coroutineContext)
    }

    fun emitInvoiceId(id: Long) {
        if (invoiceId.value == null) {
            invoiceId.value = id
        }
    }

    fun saveInvoice(
        specificExtractions: Map<String, GiniCaptureSpecificExtraction>,
        timestamp: Long
    ) {
        val invoice = Invoice(
            id = timestamp,
            amount = specificExtractions["amount"]?.value ?: "",
            bankAccount = specificExtractions["bankaccount"]?.value ?: "",
            bic = specificExtractions["bic"]?.value ?: ""
        )
        viewModelScope.launch(Dispatchers.Default) {
            homeRepository.insertInvoice(invoice)
        }
    }

}