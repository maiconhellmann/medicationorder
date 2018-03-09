package pharmacy.maicon.com.pharmacyapp.model

/**
 * Created on 09/03/2018.
 */
data class Order(
        val medicationName: String,
        val medicationType: MedicationType,
        val amount: Long,
        val distributor: Distributor,
        val branchList: List<String>
)