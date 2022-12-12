package abolfazli.mahdi.ginitask.home.ui

import abolfazli.mahdi.ginitask.BuildConfig
import abolfazli.mahdi.ginitask.R
import abolfazli.mahdi.ginitask.core.utils.GiniCaptureEventTracker
import abolfazli.mahdi.ginitask.core.utils.PermissionHandler
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.launch
import net.gini.android.bank.sdk.GiniBank
import net.gini.android.bank.sdk.capture.CaptureConfiguration
import net.gini.android.bank.sdk.capture.CaptureFlowContract
import net.gini.android.bank.sdk.capture.CaptureResult
import net.gini.android.bank.sdk.capture.ResultError
import net.gini.android.bank.sdk.network.getDefaultNetworkApi
import net.gini.android.bank.sdk.network.getDefaultNetworkService
import net.gini.android.capture.DocumentImportEnabledFileTypes
import net.gini.android.capture.GiniCaptureError
import net.gini.android.capture.network.GiniCaptureNetworkApi
import net.gini.android.capture.network.GiniCaptureNetworkService
import net.gini.android.capture.requirements.RequirementsReport
import net.gini.android.core.api.DocumentMetadata
import javax.inject.Inject


class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel
    private val permissionHandler = PermissionHandler(this)

    private lateinit var invoicesAdapter: InvoicesAdapter
    private lateinit var invoiceRV: RecyclerView

    private val captureLauncher =
        registerForActivityResult(CaptureFlowContract()) { result: CaptureResult ->
            when (result) {
                is CaptureResult.Success -> {
                    Log.e("Success", "successful")
                    homeViewModel.saveInvoice(
                        result.specificExtractions,
                        System.currentTimeMillis()
                    )
                    //handleExtractions(result.specificExtractions)
                }
                is CaptureResult.Error -> {
                    when (result.value) {
                        is ResultError.Capture -> {
                            val captureError: GiniCaptureError =
                                (result.value as ResultError.Capture).giniCaptureError
                            Log.e("Error", "ResultError.Capture")
                            //handleCaptureError(captureError)
                        }
                        is ResultError.FileImport -> {
                            // See the File Import section on the Capture Features page for more details.
                            val fileImportError = result.value as ResultError.FileImport
                            Log.e("Error", "ResultError.FileImport")
                            //handleFileImportError(fileImportError)
                        }
                    }
                }
                CaptureResult.Empty -> {
                    Log.e("Empty", "result empty")
                    //handleNoExtractions()
                }
                CaptureResult.Cancel -> {
                    Log.e("Cancel", "result canceled")
                    //handleCancellation()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val fab: FloatingActionButton = findViewById(R.id.fab_capture_homeActivity)
        fab.setOnClickListener {
            startGiniCaptureSdk()
        }

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        invoiceRV = findViewById(R.id.rv_invoices_homeActivity)
        invoicesAdapter = InvoicesAdapter {
            startActivity(Intent(intent).apply {
                setClass(this@HomeActivity, DetailsActivity::class.java)
                putExtra("id", it.id)
            })
        }
        invoiceRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        invoiceRV.adapter = invoicesAdapter

        homeViewModel.invoices.observe(this) {
            invoicesAdapter.submitList(it)
        }
    }


    private fun startGiniCaptureSdk() {
        lifecycleScope.launch {
            if (permissionHandler.grantPermission(Manifest.permission.CAMERA)) {
                val report = GiniBank.checkCaptureRequirements(this@HomeActivity)
                if (!report.isFulfilled) {
                    handleUnfulfilledRequirements(report)
                }
                launchGiniCapture()
            } else {
                Toast.makeText(
                    this@HomeActivity, "Camera Permission NOT granted!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun launchGiniCapture() {
        GiniBank.releaseCapture(this)

        val (networkService, networkApi) = getNetworkService()
        GiniBank.setCaptureConfiguration(
            CaptureConfiguration(
                networkService = networkService,
                networkApi = networkApi,
                documentImportEnabledFileTypes = DocumentImportEnabledFileTypes.PDF_AND_IMAGES,
                fileImportEnabled = true,
                qrCodeScanningEnabled = true,
                multiPageEnabled = true,
                flashButtonEnabled = true,
                eventTracker = GiniCaptureEventTracker
            )
        )
        GiniBank.startCaptureFlow(captureLauncher)
    }

    private fun handleUnfulfilledRequirements(report: RequirementsReport) {
        val stringBuilder = StringBuilder()
        report.requirementReports.forEach {
            if (!it.isFulfilled) {
                stringBuilder.append(it.requirementId)
                stringBuilder.append(": ")
                stringBuilder.append(it.details)
                stringBuilder.append("\n")
            }
        }
        Toast.makeText(
            this, "Requirements not fulfilled:\n$stringBuilder",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getNetworkService(): Pair<GiniCaptureNetworkService, GiniCaptureNetworkApi> {
        val clientId = BuildConfig.gini_api_client_id
        val clientSecret = BuildConfig.gini_api_client_secret
        val domain = "example.com"
        val documentMetadata = DocumentMetadata().apply {
            setBranchId("GiniBankExampleAndroid")
            add("AppFlow", "ComponentAPI")
        }
        val network =
            getDefaultNetworkService(this, clientId, clientSecret, domain, documentMetadata)
        return network to getDefaultNetworkApi(network)
    }

}