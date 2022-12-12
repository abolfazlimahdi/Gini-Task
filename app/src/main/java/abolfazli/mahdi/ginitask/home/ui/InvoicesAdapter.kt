package abolfazli.mahdi.ginitask.home.ui

import abolfazli.mahdi.ginitask.R
import abolfazli.mahdi.ginitask.home.ui.entities.Invoice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class InvoicesAdapter(
    private val invoice: (Invoice) -> Unit
) :
    ListAdapter<Invoice, InvoicesAdapter.InvoiceViewHolder>(
        object : DiffUtil.ItemCallback<Invoice>() {
            override fun areItemsTheSame(
                oldItem: Invoice,
                newItem: Invoice
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Invoice,
                newItem: Invoice
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_invoice,
            parent,
            false
        )
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class InvoiceViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val tvAmount: TextView = view.findViewById(R.id.tv_amount_invoiceItem)
        private val tvBankAccount: TextView = view.findViewById(R.id.tv_bankAccount_invoiceItem)
        private val tvBic: TextView = view.findViewById(R.id.tv_bic_invoiceItem)

        fun onBind(item: Invoice) {
            tvAmount.text = item.amount
            tvBankAccount.text = item.bankAccount
            tvBic.text = item.bic

            view.setOnClickListener{
                invoice.invoke(item)
            }
        }
    }
}