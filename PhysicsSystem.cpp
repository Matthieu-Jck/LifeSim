#pragma once

#include "System.h"
#include "Entity.h"
#include "PhysicsComponent.h"
#include "GeneComponent.h"

class PhysicsSystem : public System
{
public:
    void update(float deltaTime) override;
    void update(Entity& entity) override;
    void applyFriction(Entity& entity);
    void applyGravity(Entity& entity, float globalGravityValue);
    void updateCurrentWeight(Entity& entity);

    // Getter for globalGravityValue
    float getGlobalGravityValue() const {
        return globalGravityValue;
    }

    // Setter for globalGravityValue
    void setGlobalGravityValue(float value) {
        globalGravityValue = value;
    }

private:
    float globalGravityValue = 0;
};

void PhysicsSystem::applyFriction(Entity& entity) {
    PhysicsComponent& physics = entity.getComponent<PhysicsComponent>(PHYSICS_COMPONENT);
    float frictionCoefficient = 0.1f;

    physics.speed -= physics.speed * frictionCoefficient;
}

void PhysicsSystem::applyGravity(Entity& entity, float globalGravityValue) {
    // TODO: Implement gravity between entities
}

void PhysicsSystem::updateCurrentWeight(Entity& entity) {
    PhysicsComponent& physics = entity.getComponent<PhysicsComponent>(PHYSICS_COMPONENT);
    GeneComponent& gene = entity.getComponent<GeneComponent>(GENE_COMPONENT);
    // Your logic here
}

void PhysicsSystem::update(float deltaTime) {
    // Your logic here
}

void PhysicsSystem::update(Entity& entity) {
    applyFriction(entity);
    applyGravity(entity, globalGravityValue);
    updateCurrentWeight(entity);
}
