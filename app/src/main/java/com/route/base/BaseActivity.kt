package com.route.base

import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity :AppCompatActivity() {
    fun showDialoge(title:String?=null,
                    message:String,
                    posActionName:String?=null,
                    posAction: DialogInterface.OnClickListener?=null,
                    negActionName:String?=null,
                    negAction: DialogInterface.OnClickListener?=null){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setPositiveButton(posActionName,posAction)
        builder.setNegativeButton(negActionName,negAction)
        builder.show()
    }
    fun showDialoge(titleId:Int?=null,
                    messageId:Int,
                    posActionName:Int?=null,
                    posAction: DialogInterface.OnClickListener?=null,
                    negActionName:Int?=null,
                    negAction: DialogInterface.OnClickListener?=null){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(messageId)

        if(titleId!=null)
            builder.setTitle(titleId)
        if (posActionName!=null)
            builder.setPositiveButton(posActionName,posAction)
        if (negActionName!=null)
            builder.setNegativeButton(negActionName,negAction)

        builder.show()
    }

   fun makeSnackBar(
        message: String, actionSting:String?=null,
        click: View.OnClickListener?=null, view :View){
        val snackbar = Snackbar.make(view,message,5000)
        if(actionSting!=null)
            snackbar.setAction(actionSting,click)
        snackbar.show()
    }
    fun makeSnackBar(messageId: Int,
                     actionSting:Int?=null,
                     click: View.OnClickListener?=null
                     ,view: View ){

        val snackbar = Snackbar.make(view,messageId, 5000)
        if(actionSting!=null)
            snackbar.setAction(actionSting,click)
        snackbar.show()
    }

    fun makeToast(message:String){
        Toast.makeText(this,
            message, Toast.LENGTH_LONG).show()
    }
    fun makeToast(messageId:Int){
        Toast.makeText(this,
            messageId, Toast.LENGTH_LONG).show()
    }

}