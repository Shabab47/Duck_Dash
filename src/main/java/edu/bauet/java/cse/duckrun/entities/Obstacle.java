package edu.bauet.java.cse.duckrun.entities;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Obstacle {

    protected Group root;
    protected ImageView view;
    protected Rectangle hitbox;

    protected double speed;
    protected boolean active = true;
    protected boolean hasCollided = false;

    // For render interpolation (visual only; simulation/collision uses current position)
    protected double prevLayoutX = 0.0;
    protected double currLayoutX = 0.0;

    public Obstacle(double startX,
                    double startY,
                    double worldSpeed,
                    double extraSpeed,
                    double displayHeight) {

        this.speed = worldSpeed; // Ignore extraSpeed for stationary items

        view = new ImageView();
        view.setFitHeight(displayHeight);
        view.setPreserveRatio(true);

        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.TRANSPARENT); // Set to a visible color for debugging if needed

        root = new Group(view, hitbox);

        root.setLayoutX(startX);
        root.setLayoutY(startY);
        prevLayoutX = startX;
        currLayoutX = startX;
    }

    public void update(double deltaTime) {
        if (!active) return;

        prevLayoutX = currLayoutX;
        double effectiveSpeed = speed * 60; // Convert speed to pixels per second
        currLayoutX = currLayoutX - effectiveSpeed * deltaTime;
        root.setLayoutX(currLayoutX);
        updateHitbox();

        if (currLayoutX + view.getBoundsInParent().getWidth() < 0) {
            active = false;
        }
    }

    /** Apply interpolated render position (visual only). Call after simulation steps each frame. */
    public void renderInterpolated(double alpha) {
        if (!active) return;
        double x = prevLayoutX + (currLayoutX - prevLayoutX) * alpha;
        root.setLayoutX(x);
        updateHitbox();
    }

    private void updateHitbox() {
        Bounds viewBounds = view.getBoundsInParent();

        double shrinkX = viewBounds.getWidth() * getHitboxShrinkX();
        double shrinkYTop = viewBounds.getHeight() * getHitboxShrinkYTop();
        double shrinkYBottom = viewBounds.getHeight() * getHitboxShrinkYBottom();

        hitbox.setX(viewBounds.getMinX() + shrinkX);
        hitbox.setY(viewBounds.getMinY() + shrinkYTop);
        hitbox.setWidth(viewBounds.getWidth() - shrinkX * 2);
        hitbox.setHeight(viewBounds.getHeight() - shrinkYTop - shrinkYBottom);
    }

    protected double getHitboxShrinkX() { return 0.1; }
    protected double getHitboxShrinkYTop() { return 0.1; }
    protected double getHitboxShrinkYBottom() { return 0.1; }

    public Bounds getHitBox() {
        return hitbox.localToScene(hitbox.getBoundsInLocal());
    }

    public boolean isActive() { return active; }

    // --- ADDED: Methods to control collision state ---
    public boolean hasCollided() { return hasCollided; }
    public void markCollided() { hasCollided = true; }

    public Node getNode() { return root; }
}
