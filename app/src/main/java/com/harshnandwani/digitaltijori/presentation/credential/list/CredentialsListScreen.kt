package com.harshnandwani.digitaltijori.presentation.credential.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.Swipeable
import com.harshnandwani.digitaltijori.presentation.credential.list.components.SingleCredentialItem
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun CredentialsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current

    LazyColumn {
        for((entity, credential) in state.credentials) {
            item {
                Swipeable(
                    swipeToLeftEnabled = true,
                    rightColor = Color.Green,
                    rightIcon = Icons.Default.ContentCopy,
                    leftSwipeAction = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip: ClipData = ClipData.newPlainText("Username", credential.username)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Username Copied", Toast.LENGTH_SHORT).show()
                    },
                    swipeToRightEnabled = true,
                    leftColor = Color.Magenta,
                    leftIcon = Icons.Default.Edit,
                    rightSwipeAction = {
                        Toast.makeText(context, "Swpied", Toast.LENGTH_SHORT).show()
                        Log.d("MyTAG","Swiped on : $credential")
                    }

                ) {
                    SingleCredentialItem(
                        linkedEntity = entity,
                        credential = credential,
                        onClick = {  }
                    )
                }
            }
        }
    }

}
