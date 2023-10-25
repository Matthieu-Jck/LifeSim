#include "Entity.h"
#include "PositionComponent.h"
#include "GeneComponent.h"
#include "PhysicsComponent.h"
#include "StatusComponent.h"
#include <memory>
#include <sstream>
#include <debugapi.h>
#include <stdexcept>

void printEntity(const Entity& entity) {
    std::wstringstream ws;
    try {
        const auto& pos = entity.getComponent<PositionComponent>(3);
        ws << L"Entity Position: x=" << pos.x << L", y=" << pos.y << L"\n";
    }
    catch (const std::out_of_range&) {
        ws << L"Entity does not have a PositionComponent\n";
    }
    OutputDebugString(ws.str().c_str());
}
