from custom_errors import *
from abc import ABC, abstractmethod
from runner import Runner
import math

class Race(ABC):
    def __init__(self, distance, runners = None):
        if not isinstance(distance, float):
            raise CustomTypeError("distance must be a float")
        if distance <= 0:
            raise CustomValueError("distance cannot be a nagetive number")

        if runners is None:
            runners = []
        elif not all (isinstance(runner, Runner) for runner in runners):
            raise CustomTypeError("only runner obj should be in the runner list")

        
        self.runners = runners
        self.distance = distance
        self.energy_per_km = 100
    
    def add_runner(self, runner : Runner):
        if not isinstance(runner, Runner):
            raise CustomTypeError("the runner must be an instance in Runner")
        if runner in self.runners:
            raise RunnerAlreadyExistsError("runner already exists")
        if len(self.runners) >= self.maximum_participants:
            raise RaceIsFullError("race is full")
        self.runners.append(runner)
    
    def remove_runner(self, runner):
        if not isinstance(runner, Runner):
            raise CustomTypeError("the runner must be an instance in Runner")
        if runner not in self.runners:
            raise RunnerDoesntExistError("runner doesnt exists")
        self.runners.remove(runner)
    @abstractmethod    
    def conduct_race(self):
        pass

class ShortRace(Race):
    race_type = "short"
    maximum_participants = 8
    time_multiplier = 1.2

    def conduct_race(self):
        results = []
        for runner in self.runners:
            time = runner.run_race(self.race_type, self.distance) * self.time_multiplier
            results.append((runner, time))
        return results


class MarathonRace(Race):
    race_type = "long"
    maximum_participants = 16
    energy_per_km = 100

    def conduct_race(self):
        results = []
        for runner in self.runners:
            total_time = 0
            distance_remain = math.ceil(self.distance)
            for km in range(distance_remain):
                if runner.energy < self.energy_per_km:
                    results.append((runner, "DNF"))
                    break
                total_time += runner.run_race(self.race_type, 1.0)
                runner.drain_energy(self.energy_per_km)
            else:
                results.append((runner, total_time))
        return results
        
if __name__ == '__main__':
    short_race = ShortRace(0.5)
    long_race = MarathonRace(5.0)

    # Add a Runner
    eli = Runner('Elijah', 18, 'Australia', 5.8, 4.4)
    rup = Runner('Rupert', 23, 'Australia', 2.3, 1.9)

    long_race.add_runner(eli)
    long_race.add_runner(rup)

    results = long_race.conduct_race()
    for runner, time in results:
        print(runner.name, time) 
