package controller;

public enum State {

  // Basic state
  NORMAL,
  // After the first (and only) left click (line movement) on a marble
  FIRST_SELECTED_FOR_LINE,
  // After the first right click (lateral movement) on a marble
  FIRST_SELECTED_FOR_LATERAL,
  // After the second right click (lateral movement) on a marble
  SECOND_SELECTED_FOR_LATERAL,
  // After the third right click (lateral movement) on a marble
  THIRD_SELECTED_FOR_LATERAL;

}
