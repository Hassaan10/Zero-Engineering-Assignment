package com.example.zeroengineeringassignment.ui.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.core.view.ViewCompat
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.models.Priority
import com.example.zeroengineeringassignment.data.models.Status
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIData
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIModel
import com.example.zeroengineeringassignment.ui.viewmodels.HomeViewModel
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import de.charlex.compose.rememberRevealState
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.util.Date

@Composable
fun TaskList(
    list: List<TaskItemUIModel>,
    itemClick: (id: Int) -> Unit,
    onStartClick: (index: Int) -> Unit,
    onEndClick: (index: Int) -> Unit,
    onPositionChanged: (from:Int, to:Int) -> Unit,
) {
    val haptic = rememberReorderHapticFeedback()

    val lazyListState = rememberLazyListState()

    val reorderableLazyColumnState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onPositionChanged(from.index, to.index)
        haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
    }
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(list, { _, item -> item.getID() }) { index, task ->
            ReorderableItem(reorderableLazyColumnState, key = task.getID()) {
                val interactionSource = remember { MutableInteractionSource() }
                RevealSwipe(
                    backgroundStartActionLabel = "Mark Complete",
                    backgroundEndActionLabel = "Delete",
                    hiddenContentStart = {
                        Icon(
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 40.dp),
                            imageVector = Icons.Outlined.Done,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 40.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    state = rememberRevealState(
                        maxRevealDp = 150.dp,
                        directions = setOf(
                            RevealDirection.EndToStart,
                            RevealDirection.StartToEnd
                        ),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    backgroundCardEndColor = Color.Red,
                    backgroundCardStartColor = Color.Green,
                    onBackgroundStartClick = {
                        onStartClick(index)
                        true },
                    onBackgroundEndClick = {
                        onEndClick(index)
                        true },
                    card = { shape, content ->
                        Card(
                            colors = CardDefaults.cardColors().copy(
                                containerColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = shape,
                            content = content
                        )
                    }
                ) {
                    Card(
                        colors = CardDefaults.cardColors().copy(
                            containerColor = Color.White
                        ),
                        onClick = { itemClick(task.getID()) },
                        modifier = Modifier.fillMaxWidth()
                            .semantics {
                                customActions = listOf(
                                    CustomAccessibilityAction(
                                        label = "Move Up",
                                        action = {
                                            index > 0
                                        }
                                    ),
                                    CustomAccessibilityAction(
                                        label = "Move Down",
                                        action = {
                                            index < list.size - 1
                                        }
                                    ),
                                )
                            },
                        interactionSource = interactionSource
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            IconButton(
                                modifier = Modifier
                                    .draggableHandle(
                                        onDragStarted = {
                                            haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                        },
                                        onDragStopped = {
                                            haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                        },
                                        interactionSource = interactionSource,
                                    )
                                    .clearAndSetSemantics { },
                                onClick = {},
                            ) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Reorder")
                            }

                            Column(modifier = Modifier.padding(4.dp)) {
                                Text(
                                    text = task.getTitle(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                task.getDescription()
                                    ?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                Text(text = task.getStatus())
                                Text(text = task.getDueDate())
                            }

                            if (task.getStatus() == Status.PENDING.name) {
                                IconButton(
                                    onClick = { onStartClick(index) },
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Icon(Icons.Default.Info, contentDescription = "Mark Complete")
                                }
                            }
                            else {
                                Icon(Icons.Default.Done, contentDescription = "Priority")
                            }
                        }

                    }
                }
            }
        }
    }
}


//@Preview(showBackground = false)
//@Composable
//fun TaskListPreview() {
//    TaskList(list = listOf(
//        TaskItemUIData(
//            task = TaskEntity(
//                1,
//                "Task 1",
//                "Description 1",
//                Priority.HIGH.name,
//                Status.PENDING.name,
//                Date().time
//            )
//        ),
//        TaskItemUIData(
//            task = TaskEntity(
//                2,
//                "Task 2",
//                "Description 2",
//                Priority.LOW.name,
//                Status.COMPLETED.name,
//                Date().time
//            )
//        ),
//        TaskItemUIData(
//            task = TaskEntity(
//                3,
//                "Task 3",
//                "Description 3",
//                Priority.MEDIUM.name,
//                Status.PENDING.name,
//                Date().time
//            )
//        )
//    ), {}, {}, {}, {}, {},{i,j ->}, HomeViewModel())
//}
