package com.example.razorpayintegration

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.razorpayintegration.ui.theme.RazorPayIntegrationTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RazorPayIntegrationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        this@MainActivity
                    )
                }
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        Toast.makeText(this, "Payment Successful" , Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        Toast.makeText(this, "Error in payment $response" , Toast.LENGTH_LONG).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, mainActivity: MainActivity) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)){
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "image")
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "Samsung Galaxy S20", style = TextStyle(color = Color.Black))
                Text(text = "Rs 39990.00", style = TextStyle(color = Color.Black))
            }
        }

        Button(onClick = {
            makePayment(mainActivity)
        }, modifier = Modifier) {
            Text(text = "Pay now", style = TextStyle(color = Color.White))
        }
    }

}

fun makePayment(mainActivity: MainActivity) {
    val co = Checkout()
    try {
        val options = JSONObject()
        options.put("name", "DevEasy")
        options.put("description", "SUBSCRIBE NOW")
        //You can omit the image option to fetch the image from the dashboard
        options.put("image", "http://example.com/image/rzp.jpg")
        options.put("theme.color", "#3399cc");
        options.put("currency", "INR");
        options.put("amount", "50000")//pass amount in currency subunits

        /*val retryObj = JSONObject ();
        retryObj.put("enabled", true);
        retryObj.put("max_count", 4);
        options.put("retry", retryObj);*/

        val prefill = JSONObject()
        prefill.put("email", "")
        prefill.put("contact", "")

        options.put("prefill", prefill)
        co.open(mainActivity, options)
    } catch (e: Exception) {
        Toast.makeText(mainActivity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}