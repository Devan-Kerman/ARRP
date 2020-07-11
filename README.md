# ARRP
Advanced Runtime resource packs (for fabric)

Ever thought having to make 4 jsons for a single block was outrageous? Or why every single item needed it's own json too? Or wanted to create items from templates dynamically depending on the mods loaded without having to pack tons of assets into your mod?

Well RRP is for you then! RRP allows modders to generate assets and data on the fly, you could in theory write an entire mod with blocks and items with nothing but .java files and your mod json! You can make your own json templates so you don't have to make a json file for every single item you add, and even automate it if you're lazy like I am. No need to clutter your project with hundreds of basic json files.

# FAQ
### Is this compatible with resource packs?
yes, RRP uses a resource pack internally, hence the name, and it's the second to last priority resource pack next to minecraft itself,
however this means RRP cannot override mod assets, but it can override vanilla ones.

## Adding ARRP
Gradle:
```groovy
repositories {
	maven {
		url = 'https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/'
	}
}

dependencies {
    modImplementation group: 'net.devtech', name: 'arrp', version: '0.2.4'
}
```

## Using ARRP
https://github.com/Devan-Kerman/ARRP/wiki

[This repository is licenced under the MPLv2 Licence](https://github.com/Devan-Kerman/ARRP/blob/master/LICENSE)

