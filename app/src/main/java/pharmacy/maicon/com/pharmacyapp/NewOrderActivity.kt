package pharmacy.maicon.com.pharmacyapp

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_order.*
import pharmacy.maicon.com.pharmacyapp.model.Distributor
import pharmacy.maicon.com.pharmacyapp.model.MedicationType
import pharmacy.maicon.com.pharmacyapp.model.Order


/**
 * Created on 09/03/2018.
 */
class NewOrderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_order)

        initComponets()

        initListeners()
    }

    private fun initListeners() {
        buttonOk.setOnClickListener { onClickOk() }
        buttonDelete.setOnClickListener { onClickDelete() }
    }

    /**
     * Clear form data
     */
    private fun onClickDelete() {
        editTextMedicationName.text = null
        editTextAmout.text = null
        checkboxBranchPrimary.isChecked = false
        checkboxBranchSecondary.isChecked = false

        radioButtonCemefar.isChecked=false
        radioButtonCofarma.isChecked=false
        radioButtonEmpsephar.isChecked=false

        spinnerMedicationType.setSelection(0)
    }

    private fun onClickOk() {
        val medicationName = editTextMedicationName.text.toString()
        val medicationType = MedicationType.values()
                .firstOrNull { it.ordinal+1 ==  spinnerMedicationType.selectedItemPosition}

        var distributor: Distributor?= null
        when  (radioGroupDistributor.checkedRadioButtonId){
            R.id.radioButtonCemefar -> {
                distributor = Distributor.CEMEFAR
            }
            R.id.radioButtonCofarma -> {
                distributor = Distributor.COFARMA
            }
            R.id.radioButtonEmpsephar ->{
                distributor = Distributor.EMPSEPHAR
            }
        }

        val amount = editTextAmout.text.toString().toLong()

        val branchList = mutableListOf<String>()

        if(checkboxBranchPrimary.isChecked){
            branchList.add(getString(R.string.order_meditacion_branch_primary))
        }

        if(checkboxBranchSecondary.isChecked){
            branchList.add(getString(R.string.order_meditacion_branch_secondary))
        }

        if(validateForm()){
            val order = Order( medicationName, medicationType!!, amount, distributor!!, branchList)

            showViewOrder(order)
        }else{
            showErrorMandatoryFields()
        }
    }

    private fun showErrorMandatoryFields() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.mandatory_fileds_error)
                .show()
    }

    private fun showViewOrder(order: Order){
        val dialog = AlertDialog.Builder(this)
                .setView(R.layout.order_details)
                .show()

        val tvMedicationName = dialog.findViewById<TextView>(R.id.tvMedicationName)
        val tvMedicationType = dialog.findViewById<TextView>(R.id.tvMedicationType)
        val tvDistributor = dialog.findViewById<TextView>(R.id.tvMedicationDistributor)
        val tvAmount = dialog.findViewById<TextView>(R.id.tvMedicationAmount)
        val tvBranch = dialog.findViewById<TextView>(R.id.tvMedicationBranch)

        val btnOk = dialog.findViewById<Button>(R.id.buttonOk)

        tvMedicationName?.text = order.medicationName
        tvMedicationType?.text = order.medicationType.name
        tvDistributor?.text = order.distributor.name
        tvAmount?.text = order.amount.toString()
        tvBranch?.text = order.branchList.toString()

        btnOk?.setOnClickListener { dialog.dismiss() }
    }

    private fun validateForm(): Boolean {
        if(spinnerMedicationType.selectedItemPosition == 0){
            return false
        }
        return true
    }

    private fun initComponets() {
        val list = MedicationType.values().map { it.name }.toMutableList()
        list.add(0, getString(R.string.order_medication_type_select_one))

        val spinnerArrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                list)
        spinnerMedicationType.adapter = spinnerArrayAdapter
    }
}