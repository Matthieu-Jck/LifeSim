#include "Entity.h"
#include "PositionComponent.h"
#include <sstream>
#include <debugapi.h>

void printEntity(const Entity& entity) {
    std::wstringstream ws;
    try {
        auto posPtr = entity.getComponent<PositionComponent>(3);
        if (posPtr) {
            ws << L"Entity Position: x=" << posPtr->x << L", y=" << posPtr->y << L"\n";
        }
        else {
            ws << L"Entity does not have a PositionComponent\n";
        }
    }
    catch (const std::out_of_range&) {
        ws << L"Entity does not have a PositionComponent\n";
    }
    OutputDebugString(ws.str().c_str());
}
