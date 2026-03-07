import 'package:flutter/material.dart';
import 'package:milestone_3/requests.dart';
import 'package:milestone_3/tree.dart';
import 'package:milestone_3/screen_space.dart';
import 'the_drawer.dart';
import 'dart:convert' as convert;

class ScreenPartition extends StatefulWidget {
  final String id;

  const ScreenPartition({super.key, required this.id});

  @override
  State<ScreenPartition> createState() => _ScreenPartitionState();
}

class _ScreenPartitionState extends State<ScreenPartition> {
  late Future<Tree> futureTree;

  @override
  void initState() {
    super.initState();
    futureTree = getTree(widget.id);
  }

  void _refresh() async {
    setState(() {
      futureTree = getTree(widget.id);
    });
  }
 //function to unlock or lock a whole Partition from the 3 dots
  void lockUnlockArea(String action) {
    String strNow = DATEFORMATTER.format(DateTime.now());
    Uri uri = Uri.parse("${BASE_URL}/area?credential=11343&action=$action&datetime=$strNow&areaId=${widget.id}");

    sendRequest(uri).then((response) {
      _refresh();

      if (response.statusCode == 200) {
        var body = convert.jsonDecode(response.body);
        List<dynamic> results = body['requestsDoors'];

        int fallos = 0;
        // we ccount how much doors have failed
        for (var r in results) {
          // if we wanted to lock a door and the result is not locked, there has been an error
          if (action == 'lock' && r['state'] != 'locked') {
            fallos++;
          }
        }

        // we show the error message
        if (fallos > 0) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(' $fallos door(s) can not be locked because they are OPEN!!'),
              backgroundColor: Colors.orange,
              duration: const Duration(seconds: 4),
            ),
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(action == 'lock' ? 'Area Locked Successfully' : 'Area Unlocked Successfully'),
              backgroundColor: Colors.green,
            ),
          );
        }
      }
    }).catchError((error) {
      print("Error: $error");
    });
  }

// future with listview
// https://medium.com/nonstopio/flutter-future-builder-with-list-view-builder-d7212314e8c9
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
      future: futureTree,
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          var children = snapshot.data!.root.children
              .where((area) => area.id != 'exterior' && area.id != 'stairs')
              .toList();
          return Scaffold(
            drawer: widget.id == "building" ? TheDrawer(context).drawer : null,
            appBar: AppBar(
              backgroundColor: Theme.of(context).colorScheme.primary,
              foregroundColor: Theme.of(context).colorScheme.onPrimary,
              title: Text(snapshot.data!.root.id),
              actions: <Widget>[
                IconButton(icon: const Icon(Icons.home), onPressed: () {
                  Navigator.of(context).popUntil((route) => route.isFirst);
                }
                  // TODO go home page = root

                ),
                if (widget.id != "building")
                  PopupMenuButton<String>(
                    icon: const Icon(Icons.more_vert), // 3 dots icon
                    onSelected: (String value) {
                     //we call lockUnlockArea
                      if (value == 'lock_all') {
                        lockUnlockArea('lock');
                      } else if (value == 'unlock_all') {
                        lockUnlockArea('unlock');
                      }
                    },
                    itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
                      const PopupMenuItem<String>(
                        value: 'lock_all',
                        child: Text('Lock All '),
                      ),
                      const PopupMenuItem<String>(
                        value: 'unlock_all',
                        child: Text('Unlock All'),
                      ),
                    ],
                  ),
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: children.length,
              itemBuilder: (BuildContext context, int i) =>
                  _buildRow(children[i], i),
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: Center(
              child: CircularProgressIndicator(),
            ));
      },
    );
  }

  Widget _buildRow(Area area, int index) {
    assert(area is Partition || area is Space);
    if (area is Partition) {
      return ListTile(
        leading: Icon(Icons.layers, color: Colors.blue[300]),
        title: Text(area.id),
        trailing: const Icon(Icons.arrow_forward_ios, size: 16),
        onTap: () => _navigateDownPartition(area.id),
      );
    } else {
      return ListTile(
        leading: Icon(Icons.grid_view, color: Colors.orange[300]),
        title: Text(area.id),
        trailing: const Icon(Icons.arrow_forward_ios, size: 16),
        onTap: () => _navigateDownSpace(area.id),
      );
    }
  }

  void _navigateDownPartition(String childId) {
    Navigator.of(context).push(
      MaterialPageRoute<void>(
        builder: (context) => ScreenPartition(id: childId),
      ),
    );
  }

  void _navigateDownSpace(String childId) {
    Navigator.of(context).push(
      MaterialPageRoute<void>(
        builder: (context) => ScreenSpace(id: childId),
      ),
    );
  }
}
