/**
 * Created by Max on 28.03.2017.
 */
import { Spieler } from './spieler';
import { Injectable } from "@angular/core";

import { Jsonp } from '@angular/http';
import 'rxjs/add/operator/map';
import {Observable} from "rxjs";

@Injectable()
export class SpielerService {
    constructor (private jsonp: Jsonp) {}
    Spielerholen(): Observable<Spieler[]> {
        let spielerUrl = 'http://localhost:11111/sgrbadminton/rest/rangliste';
        let test = this.jsonp.get(spielerUrl).map(response => <Spieler[]> response.json());
        self.window.console.log(test);
        return test;
    }
}
