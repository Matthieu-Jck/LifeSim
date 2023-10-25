#pragma once

#include "Component.h"

class PhysicsComponent : public Component {
public:
    // Initialize with default values and specify units for each parameter.
    PhysicsComponent(float energy = 50.0f, float speed = 0.0f, float currentWeight = 1.0f)
        : energy(energy), speed(speed), currentWeight(currentWeight) {}

    float energy;         // Energy level of the entity, measured in energy units (eu). Default is 50.
    float speed;          // Current speed of the entity, measured in distance units per second (du/s). Default is 0.
    float currentWeight;  // Current weight of the entity, measured in weight units (wu). Default is 1.

    std::uint32_t getID() const override {
        return PHYSICS_COMPONENT;
    }
};
