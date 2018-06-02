package controller;

public enum State {
  // Basic state
  NORMAL,
  // After the first (and only) left click (line movement) on a ball
  FIRST_SELECTED_FOR_LINE,
  // After the first right click (lateral movement) on a ball
  FIRST_SELECTED_FOR_LATERAL,
  // After the second right click (lateral movement) on a ball
  SECOND_SELECTED_FOR_LATERAL,
  // After the third right click (lateral movement) on a ball
  THIRD_SELECTED_FOR_LATERAL;
}
