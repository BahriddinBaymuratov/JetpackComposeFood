package com.example.jetpackcompose.ui.theme.food_list

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.MainActivity
import com.example.jetpackcompose.R
import com.example.jetpackcompose.model.Category
import com.example.jetpackcompose.ui.theme.Shapes
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val viewModel: MainViewModel = viewModel()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    if (state.error.isNotBlank()) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Green,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("This is the snack bar!!")
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Image",
                    tint = Color.Black
                )
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = { // gravity start
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                title = {
                    Text(text = stringResource(R.string.jet_food))
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        drawerContent = {
            Text(text = "Drawer layout")
        },
        drawerContentColor = Color.Black
    ) {
        if (state.loading){
            Loading()
            println("@@@ ${state.loading}")
        }
        if (state.success.isNotEmpty()){
            LazyColumn(contentPadding = PaddingValues(2.dp)){
                items(state.success){category->
                    CardItem(category = category)
                }
            }
        }
        BackHandler {
            if (scaffoldState.drawerState.isOpen){
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }else{
                (context as MainActivity).finish()
            }
        }
    }
}

@Composable
fun CardItem(
    category: Category
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(120.dp),
        shape = Shapes.medium
    ) {
        val painter = rememberCoilPainter(request = category.strCategoryThumb)
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = category.strCategory,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Preview
@Composable
fun CardPreview() {

}
