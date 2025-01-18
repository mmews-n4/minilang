package de.marcusmews.minilang.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.marcusmews.minilang.MainState
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

private val colorKeywords = Color(230,130,70)
private val colorStringLiterals = Color(130,250,130)
private val colorNumberLiterals = Color(130,230,230)
private val colorError = Color(250,100,100)
private val keywords = listOf("var", "out", "print", "reduce", "map")



@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun appUI(filePath: String?) {
    val state = MainState(filePath)
    val vSplitterState = rememberSplitPaneState(0.75f)
    val hSplitterState = rememberSplitPaneState(0.55f)
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        state.reloadFile()
        focusRequester.requestFocus()
    }

    MaterialTheme(colors = darkColors()) {
        Surface(color = Color.DarkGray) {
            VerticalSplitPane(
                splitPaneState = vSplitterState,
                modifier = Modifier.fillMaxSize()
            ) {
                first {
                    HorizontalSplitPane(
                        splitPaneState = hSplitterState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        first{  editorUI(focusRequester, state) }
                        second{ varsAndOutputUI(state) }        }
                }
                second{ issuesUI(state) }
            }
        }
    }
}

/** Editor with Line Numbers */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun editorUI(
    focusRequester: FocusRequester,
    state: MainState
) {
    val annotatedString = buildHighlightedText(state.sourceCode.value.text)

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(Color.Gray).fillMaxWidth()
        ) {
            Text(state.filePath?: "BUFFER")
        }
        scrollableRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 6.dp)
                .onClick(true) { focusRequester.requestFocus() }
        ) {
            Column(modifier = Modifier.padding(end = 8.dp, top = 6.dp)) {
                for (i in 1..annotatedString.text.lines().size) {
                    Text(
                        text = "$i",
                        color = Color.Gray,
                        style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace)
                    )
                }
            }

            Box {
                val textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 16.sp
                )

                BasicTextField(
                    value = TextFieldValue(
                        selection = state.sourceCode.value.selection,
                        annotatedString = annotatedString
                    ),
                    onValueChange = { state.setSourceCode(it) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(end = 8.dp, top = 6.dp)
                        .focusRequester(focusRequester),
                    textStyle = textStyle,
                    cursorBrush = SolidColor(Color.White)
                )


                // Draw curly underlines for issues
                val charWidths = characterWidth(textStyle)
                Canvas(modifier = Modifier.fillMaxWidth().padding(end = 8.dp, top = 6.dp)) {
                    val lineHeight = 16.sp.toPx()
                    val lines = state.sourceCode.value.text.lines()

                    for (range in state.issueRanges) {
                        val startLine = range.start.line
                        val startChar = range.start.character
                        val endLine = range.end?.line ?: startLine
                        val endChar = (range.end?.character ?: startChar)
                        val endCharFix = if (startChar == endChar && startLine == endLine) endChar + 1 else endChar

                        for (line in startLine..endLine) {
                            val lineText = lines.getOrNull(line) ?: continue
                            val startX = if (line == startLine) startChar * charWidths else 0f
                            val endX = if (line == endLine) endCharFix * charWidths else lineText.length * charWidths
                            val y = line * lineHeight + lineHeight

                            drawPath(
                                path = androidx.compose.ui.graphics.Path().apply {
                                    moveTo(startX, y)
                                    var currentX = startX
                                    while (currentX < endX) {
                                        cubicTo(
                                            currentX + 2f, y - 5f,
                                            currentX + 4f, y + 5f,
                                            currentX + 6f, y
                                        )
                                        currentX += 6f
                                    }
                                },
                                color = colorError,
                                style = Stroke(width = 1.dp.toPx())
                            )
                        }
                    }
                }
            }
        }
    }
}

fun buildHighlightedText(text: String): AnnotatedString {
    val tokenRegex = Regex(
        // Match string literals
        // Match integers and floating-point numbers
        // Match words
        // Match the "->" operator
        """
        ".*?" 
        |\b\d+(\.\d+)?\b
        |\b\w+\b 
        |->
        """.trimIndent(),
        RegexOption.COMMENTS
    )

    return buildAnnotatedString {
        val lines = text.lines()
        for ((lineIndex, line) in lines.withIndex()) {
            val matches = tokenRegex.findAll(line)
            var lastIndex = 0
            for (match in matches) {
                // Append text before the match (uncolored)
                if (match.range.first > lastIndex) {
                    append(line.substring(lastIndex, match.range.first))
                }

                // Determine the color for the current token
                val token = match.value
                val color = when {
                    token.startsWith("\"") && token.endsWith("\"") -> colorStringLiterals
                    token.matches(Regex("""\d+(\.\d+)?""")) -> colorNumberLiterals
                    token == "->" -> colorKeywords
                    keywords.contains(token) -> colorKeywords
                    else -> Color.Unspecified
                }

                // Append the token with its color
                withStyle(style = SpanStyle(color = color, fontSize = 14.sp)) {
                    append(token)
                }

                lastIndex = match.range.last + 1
            }

            // Append the remaining part of the line (uncolored)
            if (lastIndex < line.length) {
                append(line.substring(lastIndex))
            }

            // Add a newline if this is not the last line
            if (lineIndex < lines.size - 1) {
                append("\n")
            }
        }
    }
}

/** Table View for Map */
@OptIn(ExperimentalSplitPaneApi::class)
@Composable
private fun varsAndOutputUI(state: MainState) {
    val vSplitterState = rememberSplitPaneState(0.65f)
    Column(
        modifier = Modifier
            .fillMaxHeight()

            .drawBehind {
                drawLine(
                    Color.LightGray,
                    Offset(0f, 0f),
                    Offset(0f, size.height),
                    2.dp.toPx()
                )
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(Color.Gray).fillMaxWidth().wrapContentHeight()
        ) {
            Text("VARIABLES")
        }

        VerticalSplitPane(
            splitPaneState = vSplitterState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            first {
                scrollableColumn(
                    Modifier
                        .padding(6.dp)
                        .fillMaxSize()
                ) {
                    state.variables.value.forEach { (name, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$name = ",
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace)
                            )
                            Text(
                                text = value,
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace)
                            )
                        }
                    }
                }
            }

            second {
                // Output View
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Gray)
                            .fillMaxWidth()
                            .drawBehind {
                                drawLine(
                                    Color.LightGray,
                                    Offset(0f, 0f),
                                    Offset(size.width, 0f),
                                    3f
                                )
                            }
                    ) {
                        Text("OUTPUT")
                    }
                    scrollableBox (
                        Modifier.padding(6.dp)
                    ) {
                        Text(
                            text = state.output.value,
                            color = Color.White,
                            style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace),
                        )
                    }
                }
            }
        }

        // Actions View
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.Gray).fillMaxWidth()
            ) {
                Text("ACTIONS")
            }

            Row (
                Modifier.padding(horizontal = 6.dp).align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!state.filePath.isNullOrEmpty()) {
                    Button(enabled = state.isEditorDirty.value, onClick = {state.reloadFile()}) { Text("Reload File", maxLines = 1, overflow = TextOverflow.Ellipsis) }
                    Button(enabled = state.isEditorDirty.value, onClick = {state.saveFile()}, modifier = Modifier.padding(horizontal = 6.dp)) { Text("Save File", maxLines = 1, overflow = TextOverflow.Ellipsis) }
                }
                Button(enabled = state.isInterpreterRunning.value, onClick = {state.cancelUpdate()}) { Text("Cancel Interpreter", maxLines = 1, overflow = TextOverflow.Ellipsis) }
            }
        }
    }
}


/** Issues View */
@Composable
private fun issuesUI(state: MainState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 1.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        Color.LightGray,
                        Offset(0f, 0f),
                        Offset(size.width, 0f),
                        3f
                    )
                }
        ) {
            Text("ISSUES")
        }
        scrollableColumn(
            Modifier.padding(6.dp)
        ) {
            for (item in state.issuesOutput) {
                Text(
                    text = item,
                    color = colorError,
                    style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Monospace),
                    modifier = Modifier.padding(end = 16.dp).fillMaxWidth()
                )
            }
        }
    }
}

/** Scrollable Column */
@Composable
private fun scrollableColumn(modifier: Modifier, content: @Composable () -> Unit) {
    scrollableBox(modifier) {
        Column {
            content()
        }
    }
}

/** Scrollable Row */
@Composable
private fun scrollableRow(modifier: Modifier, content: @Composable () -> Unit) {
    scrollableBox(modifier) {
        Row {
            content()
        }
    }
}

/** Scrollable Box */
@Composable
private fun scrollableBox(modifier: Modifier, content: @Composable () -> Unit) {
    val vScrollState = rememberScrollState()
    val hScrollState = rememberScrollState()

    Box {
        Box(modifier = modifier
            .verticalScroll(vScrollState)
            .horizontalScroll(hScrollState)
        ) {
            content()
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(vScrollState),
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd)
        )
        HorizontalScrollbar(
            adapter = rememberScrollbarAdapter(hScrollState),
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun characterWidth(style: TextStyle): Float {
    val text = "text of 21 characters"
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return widthInPixels / 21f
}
