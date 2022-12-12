package abolfazli.mahdi.ginitask.home.ui

import abolfazli.mahdi.ginitask.R
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var amount: TextView
    private lateinit var bankAccount: TextView
    private lateinit var bic: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val id = intent.extras!!.getLong("id")

        homeViewModel.emitInvoiceId(id)

        amount = findViewById(R.id.tv_amount_detailsActivity)
        bankAccount = findViewById(R.id.tv_bankAccount_detailsActivity)
        bic = findViewById(R.id.tv_bic_detailsActivity)

        homeViewModel.invoiceDetail.observe(this) {
            amount.text = it.amount
            bankAccount.text = it.bankAccount
            bic.text = it.bic

        }

    }


}
