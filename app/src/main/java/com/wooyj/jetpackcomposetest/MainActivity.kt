package com.wooyj.jetpackcomposetest

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wooyj.jetpackcomposetest.ui.theme.JetpackComposeTestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LayoutMain()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeTestTheme {
        LayoutMain()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMain() {
    Scaffold(
        topBar = { HeaderContent() }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }

}


@Composable
fun HeaderContent() {
    SmallTopAppBar(
        title = {
            Text(text = "테스트")
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    )
}


@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
//        Text("column1")
//        Text("column2")
//        SimpleList()
        LazyList()
    }
}

// List는 column, row로 그림.
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}


// Jetpack Compose의 LazyColumn은 Android 뷰의 RecyclerView와 같다.
@Composable
fun LazyList() {
//    val scrollState = rememberScrollState()
    val listSize = 100
    // state에 스크롤 위치 저장되어있음.
    val scrollState = rememberLazyListState()
    // 스크롤 움직임이 실행되는 코루틴 스코프
    val coroutineScope = rememberCoroutineScope()
//    Column(Modifier.verticalScroll(scrollState)) {
    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("최상단 스크롤")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("최하단 스크롤")
            }
        }
        LazyColumn(state = scrollState) {
//        repeat(100) {
            items(100) {
                // DSL : 도메인별 언어(DSL).
                // items : 목록 크기로 숫자를 사용할 수 있음. LazyColumn이나 LazyRow에서 쓰인다.
//            Text("Item #$it")
                ImageListItem(index = it)
            }
        }
    }
}

// 기존 RecyclerView의 ViewHolder 부분
@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.titleSmall)
    }
}


