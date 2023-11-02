// LifeForm.h

#ifndef LIFEFORM_H
#define LIFEFORM_H

#pragma once

#pragma warning(push, 0)
#include <SFML/Graphics.hpp>
#pragma warning(pop)

#include <utility>

class LifeForm {
public:
    // Constructors
    LifeForm(float base_weight, float efficiency, float acceleration,
        float attraction, float sight, float rmv);

    // Setters
    void setEnergy(float new_energy);
    void setPosition(float x, float y);
    void setSpeed(float new_speed);
    void setLifetime(float new_lifetime);
    void setCurrentWeight(float new_weight);
    void setBaseWeight(float new_base_weight);

    // Getters
    float getEnergy() const;
    std::pair<float, float> getPosition() const;
    float getSpeed() const;
    float getLifetime() const;
    float getCurrentWeight() const;
    float getBaseWeight() const;
    bool isAlive() const;

    // Other public interface methods...
    void update(float deltaTime);
    void draw(sf::RenderWindow& window) const;

private:
    // Life Form Attributes (Genes)
    float baseWeight;
    float efficiency;
    float accelerationValue;
    float attractionValue;
    float sightRange;
    float reproductionMutationValue;

    // Current State Attributes
    float energy;
    float speed;
    float lifetime;
    float currentWeight;
    std::pair<float, float> position; // Using a pair to represent x, y coordinates
    bool aliveStatus;

    // Private Methods
    void decelerate();
    void checkForDeath();
    void mutate();

    // SFML-related attributes for graphics
    sf::CircleShape shape; // Represent the life form as a circle for simplicity

    // Helper Methods
    void updateShape();
};

#endif // LIFEFORM_H
