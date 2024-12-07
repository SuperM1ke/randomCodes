import unittest
from runner import Runner
from custom_errors import CustomValueError, CustomTypeError

class TestRunner(unittest.TestCase):
    def test_runner_initialization(self):
        runner = Runner('Elijah', 18, 'Australia', 5.8, 4.4)
        
        # Check the initialization of attributes
        self.assertEqual(runner.name, 'Elijah')
        self.assertEqual(runner.age, 18)
        self.assertEqual(runner.country, 'Australia')
        self.assertEqual(runner.sprint_speed, 5.8)
        self.assertEqual(runner.endurance_speed, 4.4)
        self.assertEqual(runner.energy, 1000)

        # test invalid name
        with self.assertRaises(CustomTypeError):
            Runner(23, 18, 'Australia', 5.8, 4.4)

        # test invalid age
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 1, 'Australia', 5.8, 4.4)
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 121, 'Australia', 5.8, 4.4)
        
        # test invalid country
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 18, 'Where_is_country', 5.8, 4.4)
        
        # test invalid sprint_speed
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 18, 'Australia', 2.1, 4.4)
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 18, 'Australia', 6.9, 4.6)

        # test invalid endurance_speed
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 18, 'Australia', 5.8, 1.7)
        with self.assertRaises(CustomValueError):
            Runner('Elijah', 18, 'Australia', 5.8, 5.5)

    # test on method drain_energy
    def test_drain_energy(self):
        runner = Runner('Elijah', 18, 'Australia', 5.8, 4.4)

        #1 try valid drain
        runner.drain_energy(500)  # it should be 500 after drain
        self.assertEqual(runner.energy, 500)

        #2 drain out of range
        runner.drain_energy(501) # it should be 0 after drain cause energy cannot be nagetive
        self.assertEqual(runner.energy, 0)

        #3 drain invalid number
        with self.assertRaises(CustomValueError):
            runner.drain_energy(-1)
        with self.assertRaises(CustomValueError):
            runner.drain_energy(1001)

        #4 invalid type
        with self.assertRaises(CustomTypeError):
            runner.drain_energy('a')

    # test on method recover_energy
    def test_recover_energy(self):
        runner = Runner('Elijah', 18, 'Australia', 5.8, 4.4)
        runner.energy = 500

        #1 try valid drain frist
        runner.recover_energy(20)
        self.assertEqual(runner.energy, 520)

        #2 try recover out of range
        runner.recover_energy(481)   # should be 1000 cause max energy = 1000
        self.assertEqual(runner.energy, 1000)

        #3 try invalid number to recover
        with self.assertRaises(CustomValueError): 
            runner.recover_energy(-1)
        with self.assertRaises(CustomValueError):
            runner.recover_energy(1001)

        with self.assertRaises(CustomTypeError):
            runner.recover_energy('a')
        
    # test on method run_race
    def test_run_race(self):
        runner = Runner('Elijah', 18, 'Australia', 5.8, 4.4)

        # valid long and short race
        time_taken = runner.run_race("short", 1.0)
        self.assertAlmostEqual(time_taken, 172.41, places=2)
   
        time_taken = runner.run_race("long", 2.0)
        self.assertAlmostEqual(time_taken, 454.55, places=2)

        time_taken = runner.run_race("long", 5.6)
        self.assertAlmostEqual(time_taken, 1272.73, places=2)

        # invalid race_type
        with self.assertRaises(CustomValueError):
            runner.run_race("middle", 2.0)
        
        # invalid distance
        with self.assertRaises(CustomValueError):
            runner.run_race("long", -1.0)
        
        # invalid race_type (TypeError)
        with self.assertRaises(CustomTypeError):
            runner.run_race(123, 2.0)
        
        # invalid distance(TypeError)
        with self.assertRaises(CustomTypeError):
            runner.run_race("short", "two")
        

        
if __name__ == '__main__':
    unittest.main()
