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
