package com.abitic.monitordesempenho

import android.app.Activity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

       val nav_host_fragment =supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Passing each menu ID as a set of Ids because each
        navController = nav_host_fragment.findNavController()

        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.disciplinasFragment, R.id.navigation_perfl_f))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

const val ADD_DISCIPLINA_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_DISCIPLINA_RESULT_OK = Activity.RESULT_FIRST_USER+1

const val ADD_PROFESSOR_RESULT_OK = Activity.RESULT_FIRST_USER+2
const val EDIT_PROFESSOR_RESULT_OK = Activity.RESULT_FIRST_USER+3

const val ADD_NOTA_RESULT_OK = Activity.RESULT_FIRST_USER+4
const val EDIT_NOTA_RESULT_OK = Activity.RESULT_FIRST_USER+5