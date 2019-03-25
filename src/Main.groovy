

class Main {

    static def spit = { File file, String content ->
        println content
        file << content
    }

    static def sep = File.separator
    static def force = false

    static void main(String[] args) {

        if (args.contains("-f") || args.contains("--force")) {
            force = true
        }

        def className = args.first().capitalize()
        def folder = ".${sep}src${sep}ui${sep}"

        putRoutes(className, folder)
        putStacks(className, folder)
        putScreen(className, folder)
    }

    static Boolean checkWriting(File file) {
        if (file.exists() && file.text.length() > 0) {
            return force
        }
        return true
    }

    static def putScreen(String className, String rootFolder) {
        def lowerName = className.toLowerCase()
        def folder = "${rootFolder}${sep}screens${sep}${lowerName}"

        new File(folder).mkdirs()

        def indexOutput = new File("${folder}${sep}index.js")
        def stylesOutput = new File("${folder}${sep}${lowerName}.styles.js")
        def screenOutput = new File("${folder}${sep}${lowerName}.screen.js")

        if (checkWriting(indexOutput)) {
            indexOutput.text = ""
            spit indexOutput, "export { ${className}Screen } from './${lowerName}.screen'"
        }

        if (checkWriting(stylesOutput)) {
            stylesOutput.text = ""
            spit stylesOutput, """import { StyleSheet } from 'react-native'

const styles = StyleSheet.create({
  container: {
    flex: 1
  }
})

export { styles }
"""
        }

        if (checkWriting(screenOutput)) {
            screenOutput.text = ""
            spit screenOutput, """import React, { Component } from 'react'
import { Text } from 'react-native'
import { BaseScreen } from '@ui/screens/base'
import { styles } from './${lowerName}.styles'


export class ${className}Screen extends BaseScreen {

  renderContent() {
    return ( 
      <Text>Pague o aluguel!</Text>
    )

  }

}
"""
        }
    }

    static def putStacks(String className, String rootFolder) {
        def lowerName = className.toLowerCase()
        def folder = "${rootFolder}${sep}navigator${sep}stacks${sep}${lowerName}"

        new File(folder).mkdirs()
        def indexOutput = new File("${folder}${sep}index.js")
        def mapOutput = new File("${folder}${sep}${lowerName}.stack.js")

        if (checkWriting(indexOutput)) {
            indexOutput.text = ""
            spit indexOutput, "export { ${className}Stack } from './${lowerName}.stack'"
        }

        if (checkWriting(mapOutput)) {
            mapOutput.text = ""
            spit mapOutput, """import { createStackNavigator } from 'react-navigation'

import { ${className}Routes } from '@ui/navigator/routes/${lowerName}'

export const ${className}Stack = createStackNavigator(${className}Routes)
"""
        }

    }

    static def putRoutes(String className, String rootFolder) {
        def lowerName = className.toLowerCase()
        def upperName = className.toUpperCase()

        def folder = "${rootFolder}${sep}navigator${sep}routes${sep}${lowerName}"

        new File(folder).mkdirs()
        def indexOutput = new File("${folder}${sep}index.js")
        def routeOutput = new File("${folder}${sep}${lowerName}.route.js")
        def mapOutput = new File("${folder}${sep}${lowerName}.map.js")

        if (checkWriting(indexOutput)) {
            indexOutput.text = ""
            spit indexOutput, """export { ${upperName}_ROUTES } from './${lowerName}.route'
export { ${className}Routes } from './${lowerName}.map'
"""
        }

        if (checkWriting(routeOutput)) {
            routeOutput.text = ""
            spit routeOutput, """export const ${upperName}_ROUTES = {
    ${upperName}: '${className}Screen'
}"""
        }

        if (checkWriting(mapOutput)) {
            mapOutput.text = ""
            spit mapOutput, """import { ${upperName}_ROUTES } from './${lowerName}.route'

import { ${className}Screen } from '@ui/screens/${lowerName}'

export const ${className}Routes = {
  [${upperName}_ROUTES.${upperName}]: {
    screen: ${className}Screen
  }
}
"""
        }

    }

}
