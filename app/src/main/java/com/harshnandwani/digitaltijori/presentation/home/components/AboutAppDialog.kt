package com.harshnandwani.digitaltijori.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.R

@Composable
fun AboutAppDialog(
    isVisible: Boolean,
    onDismissRequest: (Boolean) -> Unit
) {

    if (isVisible) {

        val context = LocalContext.current
        var donotShowAgainChecked by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { onDismissRequest(donotShowAgainChecked) },
            title = { Text(text = "About Digital Tijori") },
            text = {
                Column {
                    Spacer(modifier = Modifier.size(24.dp))
                    val annotatedLinkString = buildAnnotatedString {

                        val content = context.resources.getString(R.string.about_app_content)

                        var startIndex = content.indexOf("Github")
                        var endIndex = startIndex + 6

                        append(content)

                        addStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ), start = startIndex, end = endIndex
                        )
                        addStringAnnotation(
                            tag = "URL",
                            annotation = context.resources.getString(R.string.url_to_github_repo),
                            start = startIndex,
                            end = endIndex
                        )

                        startIndex = content.lastIndexOf("Github")
                        endIndex = startIndex + 6

                        addStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ), start = startIndex, end = endIndex
                        )
                        addStringAnnotation(
                            tag = "URL",
                            annotation = context.resources.getString(R.string.url_to_manifest_file_source),
                            start = startIndex,
                            end = endIndex
                        )

                    }

                    val uriHandler = LocalUriHandler.current

                    ClickableText(
                        text = annotatedLinkString,
                        onClick = { index ->
                            annotatedLinkString
                                .getStringAnnotations("URL", index, index)
                                .forEach {
                                    it.let { stringAnnotation ->
                                        uriHandler.openUri(stringAnnotation.item)
                                    }
                                }
                        }
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = donotShowAgainChecked,
                            onCheckedChange = { donotShowAgainChecked = !donotShowAgainChecked }
                        )
                        Text(
                            text = "Don't show this again",
                            Modifier.clickable { donotShowAgainChecked = !donotShowAgainChecked }
                        )
                    }

                }
            },
            confirmButton = {
                TextButton(onClick = { onDismissRequest(donotShowAgainChecked) }) {
                    Text(text = "Close")
                }
            }
        )
    }

}