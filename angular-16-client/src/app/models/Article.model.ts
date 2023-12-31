import { Comments } from "./Comments.model";

export class Article {
    id?: number;
    title?: string;
    description?: string;
    author?: string;
    createdAt?: Date;
    likes: number= 0;
    disabled = false;
    dislikes?: number=0;
    // Add other fields as needed
    Comments?: Comments[];
  }