import 'package:flutter/material.dart';
import 'package:milestone_3/requests.dart';
import 'package:milestone_3/tree.dart';
import 'dart:convert' as convert;

class ScreenSpace extends StatefulWidget {
  final String id;

  const ScreenSpace({super.key, required this.id});

  @override
  State<ScreenSpace> createState() => _StateScreenSpace();
}

class _StateScreenSpace extends State<ScreenSpace> {
  late Future<Tree> futureTree;

  @override
  void initState() {
    super.initState();
    futureTree = getTree(widget.id);
  }

// future with listview
// https://medium.com/nonstopio/flutter-future-builder-with-list-view-builder-d7212314e8c9
  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;
    return FutureBuilder<Tree>(
      future: futureTree,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Scaffold(
            appBar: AppBar(
              backgroundColor: Theme.of(context).colorScheme.primary,
              foregroundColor: Theme.of(context).colorScheme.onPrimary,
              title: Text(snapshot.data!.root.id),
              actions: <Widget>[
                IconButton(icon: const Icon(Icons.home), onPressed: () {Navigator.of(context).popUntil((route) => route.isFirst);}
                  // TODO go home page = root
                ),
                //TODO other actions
              ],
            ),
            body: Stack(children: [ // Stack overlaps widgets
              ListView.separated(
                // it's like ListView.builder() but better because it includes a separator between items
                padding: const EdgeInsets.all(16.0),
                itemCount: snapshot.data!.root.children.length,
                itemBuilder: (BuildContext context, int i) =>
                    _buildRow(snapshot.data!.root.children[i], i),
                separatorBuilder: (BuildContext context, int index) =>
                const Divider(),
              ),
              snapshot.connectionState != ConnectionState.done
              // snapshot has (old) data but we have not yet received an answer
                  ? Positioned(
                  top: screenSize.height * 0.3, // 30% from top
                  left: screenSize.width * 0.4, // 40% from left
                  child: Container(
                      height: MediaQuery.of(context).size.height / 4,
                      width: MediaQuery.of(context).size.width / 4,
                      child: Center(
                        child: CircularProgressIndicator(),
                      )
                  ))
                  : Container(), // show nothing
            ]),
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

  void _refresh() {
    setState(() {futureTree = getTree(widget.id);});
  }



  Widget _buildRow(Door door, int index) {
    Icon icon;
    Color color;

    if (door.state == 'locked') {
      icon = const Icon(Icons.lock);
      color = Colors.red;
    } else if (door.state == 'unlocked') {
      icon = const Icon(Icons.lock_open);
      color = Colors.green;
    } else if (door.state == 'propped') {
      icon = const Icon(Icons.warning_amber_rounded);
      color = Colors.orange;
    } else if (door.state == 'unlocked_shortly') {
      icon = const Icon(Icons.timelapse);
      color = Colors.blue;
    } else {
      icon = const Icon(Icons.question_mark);
      color = Colors.grey;
    }

    return ListTile(
      leading: Icon(Icons.meeting_room, color: color, size: 30),

      title: Text('Door ${door.id}'),

      subtitle: Text(
          door.closed ? "Closed" : "Open",
          style: TextStyle(
              color: door.closed ? Colors.black : Colors.black,
          )
      ),


      trailing: PopupMenuButton<String>(
        icon: const Icon(Icons.more_vert),
        onSelected: (String result) {
          if (result == 'open') openDoor(door);
          if (result == 'close') closeDoor(door);
          if (result == 'lock') lockDoor(door);
          if (result == 'unlock') unlockDoor(door);

          Future.delayed(const Duration(milliseconds: 150), () {
            _refresh();
          });
        },
        itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
          const PopupMenuItem<String>(
            value: 'open',
            child: ListTile(leading: Icon(Icons.meeting_room_outlined), title: Text('Open')),
          ),
          const PopupMenuItem<String>(
            value: 'close',
            child: ListTile(leading: Icon(Icons.door_front_door), title: Text('Close')),
          ),
          const PopupMenuItem<String>(
            value: 'lock',
            child: ListTile(leading: Icon(Icons.lock), title: Text('Lock')),
          ),
          const PopupMenuItem<String>(
            value: 'unlock',
            child: ListTile(leading: Icon(Icons.lock_open), title: Text('Unlock')),
          ),
        ],
      ),
    );
  }
  void openDoor(Door door) {
    lockUnlockDoor(door, 'open');
  }

  void closeDoor(Door door) {
    lockUnlockDoor(door, 'close');
  }
  void lockDoor(Door door) {
    lockUnlockDoor(door, 'lock');
  }
  void unlockDoor(Door door) {
    lockUnlockDoor(door, 'unlock');
  }
  void lockUnlockDoor(Door door, String action) {
    //assert ((action=='lock') | (action=='unlock'));

    String strNow = DATEFORMATTER.format(DateTime.now());
    print(DateTime.now());
    print(strNow);

    Uri uri = Uri.parse("${BASE_URL}/reader?credential=11343&action=$action"
        "&datetime=$strNow&doorId=${door.id}");

    // credential 11343 corresponds to user Ana of Administrator group
    print('door ${door.id} is ${door.state}');
    print('${action} ${door.id}, uri $uri');

    // we use .then() to see the server response
    sendRequest(uri).then((response) {
      _refresh();

      if (response.statusCode == 200) {
        var body = convert.jsonDecode(response.body);
        String newState = body['state'];


        //if the action was LOCK and the final state is not locked, there has been an error
        if (action == 'lock' && newState != 'locked') {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Door is OPEN!'),
              backgroundColor: Colors.red,
              duration: Duration(seconds: 2),
            ),
          );
        }
      }
    });
  }
}