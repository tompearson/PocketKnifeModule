@file:Suppress("DEPRECATION")

package com.example.pocketknifemodule1


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.format.Formatter
import android.widget.Toast
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.scottyab.rootbeer.RootBeer
import java.net.NetworkInterface
import java.util.*


internal lateinit var mActivity: Context
internal lateinit var cm: ConnectivityManager

internal var mWirelessNetworkName: String? = null
internal var mIP_address: String? = null
lateinit var toast: Toast


val textMessage = StringBuilder()


fun methodWithPermissions(mythis: Context) =
    mythis.runWithPermissions(Manifest.permission.ACCESS_FINE_LOCATION) {
        // Do the stuff with permissions safely
        // TODO fix the asynchronicity of this so that it blocks until permission is granted
        toast = Toast.makeText(mythis, mythis.getString(R.string.loc_granted), Toast.LENGTH_LONG)
        toast.show()
    }

fun isItRooted(mythis: Context): String {
//  https://stackoverflow.com/questions/3424195/determining-if-an-android-device-is-rooted-programmatically
//  https://github.com/scottyab/rootbeer/blob/master/README.md
    val rootBeer = RootBeer(mythis)
    if (rootBeer.isRooted()) {
        //we found indication of root
        textMessage.append(mythis.getString(R.string.isRooted))
    } else {
        //we didn't find indication of root
        textMessage.append(mythis.getString(R.string.isNotRooted))
    }
    return textMessage.toString()
}

fun getBlueToothStatus(mythis: Context): String {
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled) {
        textMessage.append(mythis.getString(R.string.bluetooth))
        // Device does not support Bluetooth
    } else {
        textMessage.append(mythis.getString(R.string.no_bluetooth))
    }
    return textMessage.toString()
}

fun getLocationStatus(mythis: Context): String {
    mActivity = mythis

    // Find location status
    val manager = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        textMessage.append(mythis.getString(R.string.no_gps))
    } else {
        textMessage.append(mythis.getString(R.string.yes_gps))
    }
    return textMessage.toString()
}

fun getMACAddress(mythis: Context): String {
    val res1 = StringBuilder()

    try {
        mActivity = mythis
        // Get MAC address
        // get all the interfaces
        val all = Collections.list(NetworkInterface.getNetworkInterfaces())
        //find network interface wlan0
        for (networkInterface in all) {
            if (!networkInterface.name.equals("wlan0", ignoreCase = true)) continue
            //get the hardware address (MAC) of the interface
            val macBytes = networkInterface.hardwareAddress
            if (macBytes == null) {
                //return "";
                return textMessage.append(mActivity.getString(R.string.no_mac_address), mActivity)
                    .toString()
            }
            for (b in macBytes) {
                res1.append(String.format("%02X", b) + ":")
            }
            if (res1.isNotEmpty()) {
                res1.deleteCharAt(res1.length - 1)
            }
        }
    } catch (ex: Exception) { // Handles when macBytes us null. Never happened yet.
        return textMessage.append(
            mActivity.getString(R.string.mac_address),
            "GetMACAddress Exception " + ex.message,
            mActivity
        ).toString()
    }
    return textMessage.append(
        mActivity.getString(R.string.mac_address), res1.toString()
    ).toString()
}

fun getNetworkStatus(mythis: Context): String {
    mActivity = mythis

    cm = mActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo
    val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

    // This must be tested on a real phone (isConnected)
    if (isConnected) {
        if (activeNetwork?.type == ConnectivityManager.TYPE_MOBILE) {
            // Get System TELEPHONY service reference
            val tManager = mythis.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Get carrier name (Network Operator Name)
            val carrierName = tManager.networkOperatorName
            //String voiceMailNumber=tManager.getVoiceMailNumber(); permission problem TODO
            // Get Phone model and manufacturer name
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL

            //Get the phone type
            var strphoneType: String
            val phoneType = tManager.phoneType

            when (phoneType) {
                TelephonyManager.PHONE_TYPE_CDMA -> strphoneType = "CDMA"
                TelephonyManager.PHONE_TYPE_GSM -> strphoneType = "GSM"
                TelephonyManager.PHONE_TYPE_NONE -> strphoneType = "NONE"
                else -> { // this will never happen but stops FindBugs from complaining.
                    strphoneType = "Invalid phone type"
                }
            }
            textMessage.append(
                mActivity.getString(R.string.mobile_connection), carrierName,
                "\nPhone type: ", strphoneType,
                "\nManufacture: ", manufacturer,
                "\nModel: ", model
            ).toString()
        } else if (activeNetwork?.type == ConnectivityManager.TYPE_WIFI) {

            val wifiMgr: WifiManager =
                mActivity.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiMgr.connectionInfo

//                In the connected state, access to the SSID and BSSID requires
//                permissions (ACCESS_FINE_LOCATION ).
//                If such access is not allowed, WifiInfo#getSSID will return "<unknown ssid>"
//                and WifiInfo#getBSSID will return "02:00:00:00:00:00".
//
            mWirelessNetworkName = wifiInfo.ssid

            if (mWirelessNetworkName != null) {


//                textMessage.append(getString(R.string.wirelessNetworkName, mWirelessNetworkName))

                textMessage.append(
                    mythis.getString(R.string.wirelessNetworkName), mWirelessNetworkName
                ).toString()
            }
            mIP_address = Formatter.formatIpAddress(wifiMgr.connectionInfo.ipAddress)

            textMessage.append(
                mActivity.getString(R.string.wifi_connection), mIP_address
            ).toString()
//            textMessage.append(getString(R.string.wifi_connection, mIPaddress))
        } else {
//            GetMACAddress(mActivity)
        }
    } else {
        textMessage.append(
            mActivity.getString(R.string.no_wifi_or_mobile)
        ).toString()

//        textMessage.append(getString(R.string.no_wifi_or_mobile))


    }
    return textMessage.toString()
}













