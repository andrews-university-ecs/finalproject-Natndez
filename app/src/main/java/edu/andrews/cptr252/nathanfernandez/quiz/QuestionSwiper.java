package edu.andrews.cptr252.nathanfernandez.quiz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
    /**
     * Touch helper that deletes bugs when swiped left/right in the bug list.
     * The touch helper attaches to the RecyclerView containing the bug list.
     * (based on code from:
     * https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-
     7bbae1fce27e)
     */
    public class QuestionSwiper extends ItemTouchHelper.SimpleCallback {
        /** Reference to adapter generating bug views */
        private QuestionAdapter mAdapter;
        /** Delete icon to display when swiping */
        private Drawable mIcon;
        /** Background color displayed as bug view is swiped */
        private final ColorDrawable mBackground;
        public QuestionSwiper(QuestionAdapter adapter) {
            // only supporting left/right swiping (not up/down)
            super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;
            // Get reference to delete icon
            mIcon = ContextCompat.getDrawable(mAdapter.getActivity(),
                    android.R.drawable.ic_menu_delete);
            // Set background color displayed as bug is swiped
            mBackground = new ColorDrawable(Color.GRAY);
        }
        /**
         * Returns true if user drags a bug to another position in the list.
         * Our app does not support moving bugs, so the function just returns false. * @param recyclerView is the RecyclerView we are attached to.
         * @param viewHolder is the ViewHolder for the bug being dragged.
         * @param target is the ViewHolder for the new position in the list.
         * @return false since dragging is not supported by our app.
         */
        @Override
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;  // bug cannot be moved. only swiped left/right.
        }
        /**
         * User has swiped a bug off screen.
         * @param viewHolder Containing the swiped bug.
         * @param direction is ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // position (index) of swiped bug in the list
            int deletedPosition = viewHolder.getAdapterPosition();
            // Tell bug adapter to delete the swiped bug
            mAdapter.deleteQuestion(deletedPosition);
        }
        /**
         * Draw icon and background as bug is swiped.
         * @param c The canvas that the RecyclerView is drawing on.
         * @param recyclerView is the RecyclerView we are attached to.
         * @param viewHolder is the ViewHolder for the bug being dragged.
         * @param dX Horizontal distance bug is currently swiped
         * @param dY Vertical distance bug is currently swiped
         * @param actionState Type of interaction with view.
         * Either ItemTouchHelper.ACTION_STATE_DRAG or
         * ItemTouchHelper.ACTION_STATE_SWIPE.
         * @param isCurrentlyActive True if view is currently swiped by user.
         *                          False if it is animating back to its original state.
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState,
                                boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive);
            // View for bug being swiped
            View itemView = viewHolder.itemView;
            // Put icon on right side of view.
            int iconMargin = (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2; int iconTop = itemView.getTop()
                    + (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + mIcon.getIntrinsicHeight();
            int iconLeft = itemView.getRight() - iconMargin
                    - mIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            if (dX > 0) {
                // Swiping to the right. Update right boundary of background
                mBackground.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX), itemView.getBottom());
            } else if (dX < 0) {
// Swiping to the left. Update left boundary of background
                mBackground.setBounds(itemView.getRight() + ((int) dX),
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else {
                // view is unSwiped
                mIcon.setBounds(0,0,0,0);
                mBackground.setBounds(0, 0, 0, 0);
            }
            // Redraw background and icon as swipe continues or resets.
            mBackground.draw(c);
            mIcon.draw(c);
        }
    }

