import 'package:http/http.dart' as http;
import 'dart:convert' as convert;

import 'tree.dart';

import 'package:intl/intl.dart';
// this is to get class DateFormat, see https://pub.dev/packages/intl
final DateFormat DATEFORMATTER = DateFormat('yyyy-MM-ddThh:mm');
// the format Webserver wants

const String BASE_URL = "http://localhost:8080";
Future<http.Response> sendRequest(Uri uri) async {
  return http.get(uri).then( (http.Response response) {
    if (response.statusCode == 200) {
      print("statusCode=$response.statusCode");
      print(response.body);
      // If the server did return a 200 OK response, then parse the JSON.
    } else {
      // If the server did not return a 200 OK response, then throw an exception.
      print("statusCode=$response.statusCode");
      throw Exception('failed to get answer to request $uri');
    }
    return response;
  });
}


Future<Tree> getTree(String areaId) async {
  Uri uri = Uri.parse("${BASE_URL}/get_children?$areaId");
  return sendRequest(uri).then((http.Response response) {
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree(decoded);
  }
  );
}