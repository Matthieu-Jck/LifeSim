#include "PhysicsSystem.h"

void PhysicsSystem::applyFriction(Entity& entity) {
    // TODO: Replace with a more advanced friction formula
    PhysicsComponent& physics = entity.getComponent<PhysicsComponent>();
    float frictionCoefficient = 0.1f;  // Replace with your chosen friction coefficient

    physics.speed -= physics.speed * frictionCoefficient;
}

void PhysicsSystem::applyGravity(Entity& entity, float globalGravityValue) {
    // TODO: Implement gravity between entities
    // For now, this is just a stub. The real implementation would update the 
    // entity's speed and position based on globalGravityValue and the weight
    // of nearby entities.
}

void PhysicsSystem::updateCurrentWeight(Entity& entity) {
    PhysicsComponent& physics = entity.getComponent<PhysicsComponent>();
    GeneComponent& gene = entity.getComponent<GeneComponent>();

    physics.currentWeight = gene.baseWeight + ((physics.energy - 50.0f) / 5.0f);
    if (physics.currentWeight < 1.0f) {
        physics.currentWeight = 1.0f;
    }
}

void PhysicsSystem::update(float deltaTime)
{
}

void PhysicsSystem::update(Entity& entity) {
    applyFriction(entity);
    applyGravity(entity, globalGravityValue);
    updateCurrentWeight(entity);
}

