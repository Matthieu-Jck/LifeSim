#pragma once

#include "Component.h"

class GeneComponent : public Component {
public:
    float baseWeight;
    float efficiency;
    float acceleration;
    float attraction;
    float sight;
    float rmv;

    GeneComponent(float baseWeight, float efficiency, float acceleration, float attraction, float sight, float rmv)
        : baseWeight(baseWeight), efficiency(efficiency), acceleration(acceleration),
        attraction(attraction), sight(sight), rmv(rmv) {}

    std::uint32_t getID() const override {
        return 1;  // unique ID for GeneComponent
    }
};
