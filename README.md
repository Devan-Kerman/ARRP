# RRP
Runtime resource packs (for fabric)

Ever thought having to make 4 jsons for a single block was outrageous? Or why every single item needed it's own json too? Or wanted to create items from templates dynamically depending on the mods loaded without having to pack tons of assets into your mod?

Well RRP is for you then! RRP allows modders to generate assets and data on the fly, you could in theory write an entire mod with blocks and items with nothing but .java files and your mod json! You can make your own json templates so you don't have to make a json file for every single item you add, and even automate it if you're lazy like I am. No need to clutter your project with hundreds of basic json files.

# FAQ
### Is this compatible with resource packs?
yes, RRP uses a resource pack internally, hence the name, and it's the second to last priority resource pack next to minecraft itself,
however this means RRP cannot override mod assets, but it can override vanilla ones.

### Isn't this Artifice?
RRP is more focused on performance, making sure that you don't have to pay the cost of runtime generated resources and data. RRP isn't as advanced as Artifice yet because of it's lack of builders, however RRP will update to provide more advanced functionality in the future.

## Adding RRP
Gradle:
```groovy
repositories {
	maven {
		url = 'https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/'
	}
}

dependencies {
  modCompile group: 'net.devtech', name: 'rrp', version: '2.0.2'
}
```

## Using RRP
https://github.com/Devan-Kerman/RRP/wiki

Everything in this repository is licenced under the LGPLv3 Licence [https://github.com/Devan-Kerman/Stated/blob/master/LICENCE.txt]

