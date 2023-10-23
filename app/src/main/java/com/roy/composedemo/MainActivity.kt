package com.roy.composedemo

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roy.composedemo.ui.theme.ComposeDemoTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
  var a =  mutableStateOf("1");
    var testIndex = 0
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            a.value
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(a.value)
                    a.value = "sss"
                    remember { //使之具有 Compose环境

                    }
                }
            }
        }

    }



    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun Greeting(name: String) {
        var test1 by remember { mutableStateOf("00011") }
        var test2 by remember { mutableStateOf(Stu("stu")) }
        var a by remember { mutableStateOf(0) }

        Column  {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "菜单${a}", modifier = Modifier
                        .background(color = Color.Green)
                        .weight(1f)
                        .clickable {
                            test1 = "ssss${testIndex++}"
                        }, fontSize = 12.sp
                )
                test1(test1)
                test2(test2)
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "订单${name}", modifier = Modifier
                        .background(color = Color.Red)
                        .weight(1f).clickable {
                            test2.name = "stu222${testIndex++}"
                        }
                )

            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(count = 150) {
                    Text(
                        text = "条目", modifier =
                        Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .clickable {
                                a++
                            },
                        fontSize = 12.sp
                    )
                }
            }
        }


    }

    @Composable
    fun test1(str:String){ //已经被去除了状态 现在只是普通的字符串
        val s by  remember(str) {
            derivedStateOf {  //只重写了get 所以by关键字 所声明的字段是只读的
                str.toUpperCase()
            }
        }
        Text(s)
    }


    @Composable
    fun test2(str:Stu){
        val s by remember  {  //这个代码块 只能进来一次  内部derivedStateOf会监听 并触发自己的代码块
            derivedStateOf { //内部通知   可以单独使用  目前搭配了mutableStateOf使用
                str.name?.toUpperCase()
            }
        }
        Text("${s}")
    }



    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeDemoTheme {
            Greeting("Android")
        }
    }

    class Stu(name:String){ //注意不能加data 因为会重写eq 方法 导致对象相等（机会导致无法通知）不重写 会一直不相等就会 起到通知效果
        var name by mutableStateOf(name)  //起到通知效果
    }
}


