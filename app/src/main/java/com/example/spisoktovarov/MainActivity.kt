package com.example.spisoktovarov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spisoktovarov.ui.theme.SpisokTovarovTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpisokTovarovTheme {
                ItemsListScreen()
            }
        }
    }
}

@Composable
fun ItemsListScreen() {
    val unfavouriteItems = remember {
        mutableStateListOf(
            Person("Стол для работы", "PinskDrev", R.drawable.p1, 1200),
            Person("Косметичка", "BILITA", R.drawable.p2, 800),
            Person("Женские товары", "MILA", R.drawable.p3, 450),
            Person("Ручной топор", "OMMA", R.drawable.p5, 600)
        )
    }

    val favouriteItems = remember { mutableStateListOf<Person>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Кнопка для добавления товара
            AddItemButton(onClick = {
                val newItem = Person("Новый товар", "Brand", R.drawable.p1, 1000)
                unfavouriteItems.add(newItem)
            })

            SectionTitle("Неизбранные товары")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(unfavouriteItems) { person ->
                    PersonItem(
                        person = person,
                        isFavourite = false,
                        onAddToFavourite = {
                            unfavouriteItems.remove(person)
                            favouriteItems.add(person)
                        },
                        onDelete = {
                            unfavouriteItems.remove(person)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Избранные товары")
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(favouriteItems) { person ->
                    PersonItem(
                        person = person,
                        isFavourite = true,
                        onAddToFavourite = {
                            favouriteItems.remove(person)
                            unfavouriteItems.add(person)
                        },
                        onDelete = {
                            favouriteItems.remove(person)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddItemButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Добавить товар",
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Добавить товар",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun PersonItem(
    person: Person,
    isFavourite: Boolean,
    onAddToFavourite: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp)).
        size(width = 300.dp, height = 250.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(person.photoID),
                contentDescription = "Person Photo",
                modifier = Modifier
                    .size(90.dp)
                    .padding(5.dp).clip(shape = CircleShape)

            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = person.name,
                    fontSize = 20.sp
                )
                Text(
                    text = person.sName,
                    fontSize = 20.sp
                )
                Text(
                    text = "Цена: ${person.price} ₽",
                    fontSize = 18.sp,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить товар",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onDelete() }
                )
                Icon(
                    painter = painterResource(
                        id = if (isFavourite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                    ),
                    contentDescription = if (isFavourite) "Remove from Favourites" else "Add to Favourites",
                    tint = if (isFavourite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onAddToFavourite() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpisokTovarovTheme {
        ItemsListScreen()
    }
}

