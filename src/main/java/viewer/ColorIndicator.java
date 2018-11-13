package viewer;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorIndicator extends HBox {

  private ObservableList<Color> colors;

  private int colorId = 0;

  public ColorIndicator (ObservableList<Color> colors) {
    //super();
    this.colors = colors;
    setSpacing(2);
    display();
    colors.addListener((ListChangeListener) e -> display());
  }

  private void display () {
    colorId = colorId % colors.size();
    getChildren().clear();
    Rectangle rectangle;
    for (int i = 0; i < colors.size(); i++) {
      rectangle = new Rectangle(15, 15);
      rectangle.setStroke(colorId == i ? Color.RED : colors.get(i));
      rectangle.setFill(colors.get(i));
      getChildren().add(rectangle);
    }
  }

  public void addCustomColor (Color color) {
    colors.set(colorId++, color);
  }

  public Color[] toColorArray () {
    return colors.toArray(new Color[colors.size()]);
  }
}

