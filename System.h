#pragma once
#include <vector>
#include "Entity.h"

class System {
public:
    virtual ~System() {}
    virtual void update(float deltaTime) = 0;
    virtual void update(Entity& entity) = 0;
};

class MovementSystem : public System {
public:
    void update(float deltaTime) override {
        // Loop over entities with Position and Velocity components and update them
    }
};

class RenderSystem : public System {
public:
    void update(float deltaTime) override {
        // Loop over entities with Renderable component and render them
    }
};
