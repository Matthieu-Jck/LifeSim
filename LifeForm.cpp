#pragma once

#include "LifeForm.h"

#pragma warning(push, 0)
#include <SFML/Graphics.hpp>
#pragma warning(pop)

LifeForm::LifeForm(float base_weight, float efficiency, float acceleration, float attraction, float sight, float rmv)
    : baseWeight(base_weight),
    efficiency(efficiency),
    accelerationValue(acceleration),
    attractionValue(attraction),
    sightRange(sight),
    reproductionMutationValue(rmv),
    energy(100.0f), // Default energy
    speed(0.0f),    // Default speed
    lifetime(0.0f), // Default lifetime
    currentWeight(base_weight), // Set current weight to base weight
    aliveStatus(true), // Default alive status
    shape() {         // Initialize shape
    // Set the initial position of the lifeform
    setPosition(0.0f, 0.0f);
    // Further initialization as necessary
    updateShape();
}

// Public Methods
void LifeForm::update(float deltaTime) {
    // Update the life form state with the delta time
    decelerate();
    checkForDeath();
    mutate();
    // TODO: Add movement and interaction logic

    lifetime += deltaTime;
}

void LifeForm::draw(sf::RenderWindow& window) const {
    if (aliveStatus) {
        window.draw(shape);
    }
}

void LifeForm::setEnergy(float new_energy) {
    energy = new_energy;
    checkForDeath();
}

float LifeForm::getEnergy() const {
    return energy;
}

// Position setters and getters
void LifeForm::setPosition(float x, float y) {
    position = { x, y };
    updateShape(); // Update the graphical representation position
}

std::pair<float, float> LifeForm::getPosition() const {
    return position;
}

void LifeForm::setSpeed(float new_speed) {
    speed = new_speed;
}

float LifeForm::getSpeed() const {
    return speed;
}

void LifeForm::setLifetime(float new_lifetime) {
    lifetime = new_lifetime;
}

float LifeForm::getLifetime() const {
    return lifetime;
}

void LifeForm::setCurrentWeight(float new_weight) {
    currentWeight = new_weight;
    updateShape(); // Update the graphical representation when the weight changes
}

float LifeForm::getCurrentWeight() const {
    return currentWeight;
}

bool LifeForm::isAlive() const {
    return aliveStatus;
}

void LifeForm::setBaseWeight(float new_base_weight) {
    baseWeight = new_base_weight;
    setCurrentWeight(new_base_weight); // Update current weight if base weight is changed
}

float LifeForm::getBaseWeight() const {
    return baseWeight;
}

void LifeForm::decelerate() {
    // This is a simple deceleration model
    const float frictionCoefficient = 0.05f; // This value should be determined based on your simulation requirements
    speed -= frictionCoefficient * speed;
    if (speed < 0) speed = 0; // Speed should not be negative
}

void LifeForm::checkForDeath() {
    if (energy <= 0.0f || currentWeight < baseWeight * 0.5f) { // An example condition for death, based on weight
        aliveStatus = false;
        shape.setFillColor(sf::Color::Black); // Change color to black upon death
    }
}

void LifeForm::mutate() {
    // Example mutation effect on efficiency
    const float mutationRate = 0.01f; // This should be determined based on your simulation requirements
    efficiency += (rand() % 3 - 1) * mutationRate; // Randomly increase, decrease, or keep the same
    // You can add similar mutations for other attributes
}

void LifeForm::updateShape() {
    // Set the size of the shape based on the life form's current weight
    shape.setRadius(currentWeight);
    shape.setFillColor(sf::Color::Green); // Default color, can represent different states or types
    shape.setPosition(position.first, position.second);
}

// Implement the remaining methods...
