import 'package:flutter/material.dart';
import 'screen_partition.dart';

class TheDrawer {
  late Drawer drawer;

  TheDrawer(BuildContext context) {
    drawer = Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          DrawerHeader(
            decoration: BoxDecoration(
              color: Theme.of(context).colorScheme.primary,
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                const Text(
                  'ACS',
                  style: TextStyle(color: Colors.white, fontSize: 24, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
              ],
            ),
          ),

          // Home
          ListTile(
            leading: const Icon(Icons.apartment),
            title: const Text('Building'),
            onTap: () {
              // we close the menu
              Navigator.of(context).pop();
              // we go to the main page
              Navigator.of(context).popUntil((route) => route.isFirst);
            },
          ),


          // we have reutilized the same bar app from the exercise
          ListTile(
            leading: const Icon(Icons.people_alt),
            title: const Text('Users'),
            onTap: () {
              Navigator.of(context).pop();
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Not implemented yet...')),
              );
            },
          ),

          const Divider(),

          ListTile(
            leading: const Icon(Icons.settings),
            title: const Text('Settings'),
            onTap: () {
              Navigator.of(context).pop();
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Not implemented yet...')),
              );
            },
          ),
        ],
      ),
    );
  }
}