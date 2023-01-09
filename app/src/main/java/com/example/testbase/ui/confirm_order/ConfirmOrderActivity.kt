package com.example.testbase.ui.confirm_order

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acom.service.user.payment.IPaymentService
import com.acom.service.user.payment.PaymentNetwork
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.databinding.ActivityConfirmOrderBinding
import com.example.testbase.model.*
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.ui.cart.adapter.CartProductAdapter
import com.example.testbase.ui.confirm_order.adapter.OrderConfirmAdapter
import com.example.testbase.ui.confirm_order.adapter.OrderProductAdapter
import com.example.testbase.ui.main.MainActivity
import com.example.testbase.ui.main.MainSellerActivity
import com.example.testbase.ui.select_address.SelectAddressActivity
import com.example.testbase.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmOrderActivity : BaseActivity<ConfirmOrderViewModel, ActivityConfirmOrderBinding>() {

    private val keyId = "id"
    private var paymentSheet: PaymentSheet? = null
    private var totalPriceStr = ""

    @Inject
    lateinit var mAdapter: OrderConfirmAdapter
    var order = Order()
    var seller = Seller()

    override fun getContentLayout(): Int {
       return R.layout.activity_confirm_order
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ConfirmOrderViewModel::class.java)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rcv.layoutManager = layoutManager
        binding.rcv.adapter = mAdapter
        viewModel.getCart(FirebaseUtil.getUid())
        viewModel.getAllCartItemInCart(FirebaseUtil.getUid())
        addData()
    }

    private fun getLocalAddress() {
        if (SharePreferenceUtil.getAddress(applicationContext) == -1) {
            viewModel.getDefaultShip()
        } else {
            viewModel.getShip(SharePreferenceUtil.getAddress(applicationContext))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.log("Go ondesstry")
        SharePreferenceUtil.addAdress(-1, applicationContext)
    }

    override fun onResume() {
        super.onResume()
        LogUtil.log("Go resum confirm order")
        getLocalAddress()
    }


    override fun initListener() {
        binding.btnSave.setOnClickListener {
            if (binding.rbNow.isChecked) {
                payNow(totalPriceStr)
            } else {
                handleSendItem("Thanh toán khi nhận hàng")
            }
        }

        binding.tvSelectAddress.setOnClickListener {
            startActivity(Intent(this@ConfirmOrderActivity, SelectAddressActivity::class.java))
        }


    }

    private fun handleSendItem(typePayment: String) {
        viewModel.createOrder(binding.edtDescription.text.toString(), typePayment)
    }

    @SuppressLint("SetTextI18n")
    override fun observerLiveData() {
        viewModel.stateCart.observe(this@ConfirmOrderActivity) {
            totalPriceStr = it.totalPrice.toString()
            binding.tvTotalPrice.text = Util.converCurrency(it.totalPrice.toDouble())
            order.totalPrice = it.totalPrice
            order.totalQuantity = it.totalQuantity
            order.cart = it
        }

        viewModel.stateListAllProduct.observe(this@ConfirmOrderActivity) {
            seller = it.get(0).product.seller
            val setIdSeller = hashSetOf<Seller>()
            for (item in it) {
                setIdSeller.add(item.product.seller)
            }
            val listData = arrayListOf<CartItemConfirm>()
            setIdSeller.forEach { data ->
                val listCartItem = it.filter { cartItem ->
                    cartItem.product.seller.id.equals(data.id)
                }
                val cartItemConfirm =  CartItemConfirm(data, listCartItem as ArrayList<CartItem>)
                listData.add(cartItemConfirm)
            }

            mAdapter.setData(listData)
            viewModel.listConfirmOrder = listData
        }

        viewModel.stateCreateOrder.observe(this@ConfirmOrderActivity) {
//            FirebaseUtil.changeStatusOrder(it.data.id!!, Const.STATUS_ORDER_CONFIRM)
            if (it) {
                showErrorStr("Ban da dat hang thanh cong, xem trong phan don hang ban nhe")
                startActivity(Intent(this@ConfirmOrderActivity, MainActivity::class.java))
                viewModel.stateCreateOrder.value = false
                finishAffinity()
            }
        }

        viewModel.stateDefaultShip.observe(this@ConfirmOrderActivity) {
            if (it != null) {
                showDataDefault(it)
                viewModel.stateDefaultShip.value = null
                SharePreferenceUtil.addAdress(it.id!!, applicationContext)
            }
        }

        viewModel.stateShip.observe(this@ConfirmOrderActivity) {
            if (it != null) {
                showDataDefault(it)
            }
        }
    }

    private fun showDataDefault(it: ShippingInformation) {
        binding.tvName.text = it.name
        binding.tvPhone.text = it.phone
        binding.tvAddress.text = it.address
        binding.tvDefault.visibility = if (it.isDefault) View.VISIBLE else View.GONE
    }


    private fun addData() {
        PaymentConfiguration.init(this, Const.PUB_KEY_PAYMENT)
        paymentSheet = PaymentSheet(
            this
        ) { paymentSheetResult: PaymentSheetResult? ->
            if (paymentSheetResult != null) {
                this.onPayment(
                    paymentSheetResult
                )
            }
        }
    }

    private fun onPayment(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                Log.d("PAYMENT_STATUS", "success")
                handleSendItem("Thanh toán trực tiếp")
            }
            is PaymentSheetResult.Failed -> {
                Log.d("PAYMENT_STATUS", "Failure")
//                showError("Thanh toán không thành công!")
            }
            is PaymentSheetResult.Canceled -> {
//                showError("Thanh toán không thành công!")
            }
        }
    }

    private fun paymentFlow(emPriceKey: String, userPaymentId: String, userSecret: String) {
        paymentSheet!!.presentWithPaymentIntent(
            userSecret,
            PaymentSheet.Configuration(
                "kaka",
                PaymentSheet.CustomerConfiguration(
                    userPaymentId,
                    emPriceKey
                )
            )
        )
    }

    private fun getClientSecret(emPriceKey: String, userPaymentId: String, totalPrice: String) {
        PaymentNetwork()
            .payment()
            .create(IPaymentService::class.java)
            .createPayment(Const.SECRET_KEY_PAYMENT, userPaymentId, totalPrice, "vnd", "true")
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    val jsonObject = JSONObject(response.body().toString())
                    paymentFlow(emPriceKey, userPaymentId, jsonObject.getString("client_secret"))
                    // binding.process.visibility = View.GONE
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                }

            })
    }

    private fun getEmPriceKey(userPaymentId: String, totalPrice: String) {
        PaymentNetwork()
            .payment()
            .create(IPaymentService::class.java)
            .getEmPriceKey(Const.SECRET_KEY_PAYMENT, userPaymentId, "2020-08-27")
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    val jsonObject = JSONObject(response.body().toString())
                    getClientSecret(jsonObject.getString(keyId), userPaymentId, totalPrice)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }

            })
    }

    private fun payNow(totalPrice: String) {
        PaymentNetwork()
            .payment()
            .create(IPaymentService::class.java)
            .createCustomerPayment(Const.SECRET_KEY_PAYMENT)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    val jsonObject = JSONObject(response.body().toString())
                    val userPaymentId = jsonObject.getString(keyId)
                    getEmPriceKey(userPaymentId, totalPrice)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                }

            })
    }


}