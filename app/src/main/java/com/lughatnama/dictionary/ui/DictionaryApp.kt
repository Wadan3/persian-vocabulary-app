package com.lughatnama.dictionary.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.lughatnama.dictionary.data.DictionaryEntry
import com.lughatnama.dictionary.ui.theme.Amber

@Composable
fun DictionaryApp(viewModel: DictionaryViewModel) {
    val state by viewModel.state.collectAsState()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f),
                                MaterialTheme.colorScheme.background,
                            ),
                        ),
                    ),
            ) {
                DecorativeBackground()
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxSize()
                        .widthIn(max = 760.dp)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(horizontal = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Header()
                    SearchField(
                        value = state.query,
                        onValueChange = viewModel::onQueryChanged,
                        onClear = viewModel::clearQuery,
                    )
                    Spacer(Modifier.height(20.dp))
                    ResultsArea(
                        state = state,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BookGlyph(
            modifier = Modifier.size(44.dp),
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "لغت‌نامه",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OrnamentLine()
            Text(
                text = "واژه و معنای آن، بی نیاز از اینترنت",
                modifier = Modifier.padding(horizontal = 11.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            OrnamentLine()
        }
    }
}

@Composable
private fun OrnamentLine() {
    Box(
        modifier = Modifier
            .width(26.dp)
            .height(1.dp)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.58f)),
    )
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current
    val haloColor by animateColorAsState(
        targetValue = if (isFocused) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.10f)
        },
        animationSpec = tween(220),
        label = "searchHalo",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(27.dp))
            .border(5.dp, haloColor, RoundedCornerShape(27.dp)),
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            placeholder = {
                Text(
                    text = "جستجوی لغت",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f),
                )
            },
            leadingIcon = {
                SearchGlyph(
                    modifier = Modifier.size(23.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            trailingIcon = if (value.isNotEmpty()) {
                {
                    IconButton(onClick = onClear) {
                        ClearGlyph(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            } else {
                null
            },
            shape = RoundedCornerShape(22.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.merge(
                TextStyle(
                    textAlign = TextAlign.End,
                    textDirection = TextDirection.ContentOrRtl,
                ),
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboard?.hide() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@Composable
private fun ResultsArea(
    state: DictionaryUiState,
    modifier: Modifier = Modifier,
) {
    val contentState = when {
        state.loadError -> ContentState.Error
        state.isLoading -> ContentState.Loading
        state.query.isBlank() -> ContentState.Empty
        state.results.isEmpty() -> ContentState.NoResults
        else -> ContentState.Results
    }

    Crossfade(
        targetState = contentState,
        modifier = modifier,
        animationSpec = tween(260),
        label = "dictionaryState",
    ) { target ->
        when (target) {
            ContentState.Loading -> CenteredMessage {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp,
                )
            }

            ContentState.Error -> EmptyMessage(
                title = "لغت نامه باز نشد",
                message = "برنامه را دوباره باز کنید.",
            )

            ContentState.Empty -> EmptyMessage(
                title = "لغت مورد نظر خود را جستجو کنید",
                message = "نتیجه به صورت فوری از همین لغت نامه نمایش داده می شود.",
            )

            ContentState.NoResults -> EmptyMessage(
                title = "این لغت در لغت نامه موجود نیست.",
                message = "املای واژه را بررسی کنید و دوباره بنویسید.",
            )

            ContentState.Results -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(13.dp),
            ) {
                items(
                    items = state.results,
                    key = { entry -> entry.id },
                ) { entry ->
                    ResultCard(entry)
                }
                item { Spacer(Modifier.height(10.dp)) }
            }
        }
    }
}

@Composable
private fun ResultCard(entry: DictionaryEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.76f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 20.dp),
        ) {
            Text(
                text = entry.word,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
            )
            Spacer(Modifier.height(11.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f),
                thickness = 1.dp,
            )
            Spacer(Modifier.height(13.dp))
            Text(
                text = entry.meaning,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
private fun EmptyMessage(
    title: String,
    message: String,
) {
    CenteredMessage {
        Column(
            modifier = Modifier
                .widthIn(max = 420.dp)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BookGlyph(
                modifier = Modifier.size(54.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.68f),
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun CenteredMessage(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun DecorativeBackground() {
    val isDark = isSystemInDarkTheme()
    val color = if (isDark) Amber.copy(alpha = 0.055f) else Amber.copy(alpha = 0.075f)
    Canvas(Modifier.fillMaxSize()) {
        val radius = size.minDimension * 0.42f
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(size.width * 0.06f, size.height * 0.02f),
            style = Stroke(width = 1.2f),
        )
        drawCircle(
            color = color,
            radius = radius * 0.68f,
            center = Offset(size.width * 0.94f, size.height * 0.94f),
            style = Stroke(width = 1.2f),
        )
    }
}

@Composable
private fun SearchGlyph(
    modifier: Modifier,
    color: Color,
) {
    Canvas(
        modifier.semantics { contentDescription = "جستجو" },
    ) {
        val stroke = size.minDimension * 0.09f
        drawCircle(
            color = color,
            radius = size.minDimension * 0.29f,
            center = Offset(size.width * 0.44f, size.height * 0.42f),
            style = Stroke(width = stroke),
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.65f, size.height * 0.64f),
            end = Offset(size.width * 0.88f, size.height * 0.87f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun ClearGlyph(
    modifier: Modifier,
    color: Color,
) {
    Canvas(
        modifier.semantics { contentDescription = "پاک کردن جستجو" },
    ) {
        val stroke = size.minDimension * 0.10f
        drawLine(
            color = color,
            start = Offset(size.width * 0.25f, size.height * 0.25f),
            end = Offset(size.width * 0.75f, size.height * 0.75f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.75f, size.height * 0.25f),
            end = Offset(size.width * 0.25f, size.height * 0.75f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun BookGlyph(
    modifier: Modifier,
    color: Color,
) {
    Canvas(
        modifier.semantics { contentDescription = "کتاب" },
    ) {
        val stroke = size.minDimension * 0.055f
        val centerX = size.width / 2f
        val top = size.height * 0.16f
        val bottom = size.height * 0.82f
        val left = size.width * 0.12f
        val right = size.width * 0.88f
        val path = Path().apply {
            moveTo(centerX, size.height * 0.28f)
            cubicTo(size.width * 0.38f, top, size.width * 0.24f, top, left, size.height * 0.22f)
            lineTo(left, bottom)
            cubicTo(size.width * 0.28f, size.height * 0.76f, size.width * 0.40f, size.height * 0.80f, centerX, size.height * 0.90f)
            cubicTo(size.width * 0.60f, size.height * 0.80f, size.width * 0.72f, size.height * 0.76f, right, bottom)
            lineTo(right, size.height * 0.22f)
            cubicTo(size.width * 0.76f, top, size.width * 0.62f, top, centerX, size.height * 0.28f)
        }
        drawPath(path, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round))
        drawLine(
            color = color,
            start = Offset(centerX, size.height * 0.28f),
            end = Offset(centerX, size.height * 0.87f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
        drawArc(
            color = color.copy(alpha = 0.52f),
            startAngle = 205f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(size.width * 0.23f, size.height * 0.33f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.24f, size.height * 0.18f),
            style = Stroke(width = stroke * 0.58f),
        )
    }
}

private enum class ContentState {
    Loading,
    Error,
    Empty,
    NoResults,
    Results,
}
