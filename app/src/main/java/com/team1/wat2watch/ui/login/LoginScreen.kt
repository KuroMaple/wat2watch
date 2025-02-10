import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.wat2watch.R

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background_image),
            contentDescription = stringResource(id = R.string.login_background_image_description),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 64.sp,
            fontFamily = FontFamily.Default,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.login_subheading),
            fontSize = 18.sp,
            fontFamily = FontFamily.Default,
            color = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextFieldWithLabel(label = stringResource(id = R.string.email_label), hint = "Enter email")
        TextFieldWithLabel(label = stringResource(id = R.string.password_label), hint = "Enter password")

        Text(
            text = stringResource(id = R.string.forgot_password),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = { /* Handle Sign-In */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.sign_in_button_text), fontSize = 18.sp)
        }

        Text(
            text = stringResource(id = R.string.or),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = { /* Handle Google Sign-In */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.sign_in_with_google), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.wat2watch_logo),
            contentDescription = stringResource(id = R.string.wat2watch_logo),
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = stringResource(id = R.string.new_to_wat2watch),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun TextFieldWithLabel(label: String, hint: String) {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(hint) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
