package com.naomi.shiftmaster.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naomi.shiftmaster.data.model.Shift

@Composable
fun ShiftCard(shift: Shift) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // empty circle
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(2.dp, Color.Gray, shape = CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(text = "Employee: ${shift.employeeId}", fontSize = 14.sp)
                    Text(text = shift.date, fontSize = 12.sp, color = Color.Gray)
                }
            }

            // shift type pill
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = when (shift.shiftType) {
                    "Morning" -> Color(0xFF2E5D47)
                    "Evening" -> Color(0xFF6E6E6E)
                    "Night"   -> Color(0xFF4A4A4A)
                    else      -> Color.LightGray
                }
            ) {
                Text(
                    text = shift.shiftType,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}
