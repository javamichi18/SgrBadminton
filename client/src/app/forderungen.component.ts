/**
 * Created by Max on 06.04.2017.
 */
import {Component, Injectable, OnInit} from "@angular/core";
import {ForderungenService} from "./forderungen.service";
import {Spiel} from "./spiel";
import {Observable} from "rxjs";

@Component({
    selector: 'app-forderungen',
    templateUrl: './forderungen.component.html',
    providers: [ ForderungenService ]
})

@Injectable()
export class ForderungenComponent implements OnInit {
    forderungen: Observable<Spiel[]>;

    ngOnInit() {
        this.forderungen = this.forderungenService.obsForderungenholen();
    }

    constructor (private forderungenService: ForderungenService) {
    }
}