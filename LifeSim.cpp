#include <iostream>

#pragma warning(push, 0)
#include <SFML/Graphics.hpp>
#include <SFGUI/SFGUI.hpp>
#include <SFGUI/Widgets.hpp>
#pragma warning(pop)

#include "Simulation.h"

int main() {
    Simulation sim;
    sim.run();
    return 0;
}
