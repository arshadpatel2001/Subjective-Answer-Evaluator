#define __STDC_WANT_LIB_EXT1__ 1
#include<iostream>
#include<fstream>
#include<cctype>
#include<vector>
#include<cstring>
#include<algorithm>
#include <string.h>
using namespace std;
struct node {
    char wData;
    bool wEnd;
    vector<node*> wChildren;

    node() {
        wData = ' ';
        wEnd = false;
    }
};

node* root;

char Data(node* root) {
    return root->wData;
}

node setData(char c, node* root) {
    root->wData = c;
    return *root;
}

bool WordTerminate(node* root)
{
    return root->wEnd;
}

node setWordTerminate(node* root)
{
    root->wEnd = true;
    return *root;
}

void appendChild(node* tmp, node* current)
{
    current->wChildren.push_back(tmp);

}

vector<node*> children(node* root)
{
    return root->wChildren;
}

node* findingChild(char c, node* root)
{
    for (int ii = 0; ii < root->wChildren.size(); ii++)
    {
        node* temp1 = root->wChildren.at(ii);//returns the character at the specified position
        if (Data(temp1) == c)
        {
            return temp1;
        }
    }
    return NULL;
}

node Trie(node* root) {
    root = new node();
    return *root;
}

void addWord(string s, node* root)
{
    node* current = root;
    if (s.length() == 0)
    {
        setWordTerminate(current); // an empty word
        return;
    }
    for (int ii = 0; ii < s.length(); ii++)
    {
        node* child = findingChild(s[ii], current);
        if (child != NULL)
        {
            current = child;
        }
        else
        {
            node* tmp = new node();
            setData(s[ii], tmp);
            appendChild(tmp, current);
            current = tmp;
        }
        if (ii == s.length() - 1)
            setWordTerminate(current);
    }
}

bool searchWord(string s)
{
    node* current = root;
    while (current != NULL)
    {
        for (int i = 0; i < s.length(); i++)
        {
            node* temp1 = findingChild(s[i], current);
            if (temp1 == NULL)
                return false;
            current = temp1;
        }
        if (WordTerminate(current))
            return true;
        else
            return false;
    }
    return false;
}


void parseTree(node* current, char* s, vector<string>& res, bool& loop)
{
    char k[100] = { 0 };
    char aa[2] = { 0 };
    if (loop)
    {
        if (current != NULL)
        {
            if (WordTerminate(current) == true)
            {
                res.push_back(s);
                if (res.size() > 20)
                    loop = false;
            }
            vector<node*> child = children(current);
            for (int jj = 0; jj < child.size() && loop; jj++)
            {
                strcpy_s(k, s);
                //strcpy(k, s);
                aa[0] = Data(child[jj]);
                aa[1] = '\0';
                strcat_s(k, aa);
                //strcat(k, aa);
                if (loop)
                    parseTree(child[jj], k, res, loop);
            }
        }
    }
}

bool Complete(string s, vector<string>& res, node* root)
{
    node* current = root;
    for (int ii = 0; ii < s.length(); ii++)
    {
        node* tmp = findingChild(s[ii], current);
        if (tmp == NULL)
            return false;
        current = tmp;
    }
    char c[100];
    strcpy_s(c, s.c_str());
    //strcpy(c, s.c_str());
    bool loop = true;
    parseTree(current, c, res, loop);
    return true;
}


bool loadDictionary(node* trie, string fn)
{
    ifstream words;
    ifstream input;
    cout << "Entered in load dictionary" << endl;
    words.open(fn.c_str());
    if (!words.is_open())
    {
        cout << "Could not open Dictionary file" << endl;
        return false;
    }
    while (!words.eof())
    {



        char s[100];
        words >> s;

        addWord(s, trie);
    }
    cout << "Dict loaded";
    words.close();
    input.close();
    return true;
}

void WriteNewWord(node* trie)
{
    cout << "Enter the word : ";
    string NewWord;
    cin >> NewWord;
    bool OnlyAlpha = true;
    for (int i = 0; i < NewWord.length(); i++)
    {
        if (!isalpha(NewWord[i]))
        {
            OnlyAlpha = false;
            break;
        }
    }
    if (OnlyAlpha)
    {
        transform(NewWord.begin(), NewWord.end(), NewWord.begin(), ::tolower);
        vector<string> ListOfWords;
        Complete(NewWord, ListOfWords, trie);
        if (ListOfWords.size() != 0)
        {
            cout << "The word '" << NewWord << "' already exists in the dictionary.\n";
            return;
        }
        else
        {
            ofstream out;
            out.open("dictionary.txt", ios::app);
            if (!out.is_open())
            {
                cout << "Sorry!\nCould not open the dictionary!\n";
                out.close();
                return;
            }
            else
            {
                out << NewWord << "\n";
                cout << "Successfully loaded in the dictionary!\n";
                out.close();
                addWord(NewWord, trie);
                return;
            }
        }
    }
    else
    {
        cout << "\nNot a valid word!\n";
        return;
    }
}

int main()
{
    node* trie = new node(); //Where new is used to allocate memory for a C++ class object, the object's constructor is called after the memory is allocated
    char mode;
    cout << "Loading the dictionary file" << endl;
    loadDictionary(trie, "dictionary.txt");
    while (1)
    {
        cout << endl << endl;
        cout << "Interactive mode,press " << endl;
        cout << "1: Auto Complete Feature" << endl;
        cout << "2: Enter new words into the dictionary\n";
        cout << "3: Quit" << endl << endl;
        cin >> mode;
        if (isalpha(mode))
        {
            cout << "Invalid Input!\n";
            cout << "Enter either 1 or 2..";
            continue;
        }
        switch (mode)
        {
        case '1':
        {
            string s;
            cout << "Enter the partial word : ";
            cin >> s;
            transform(s.begin(), s.end(), s.begin(), ::tolower);
            vector<string> ListOfWords;
            Complete(s, ListOfWords, trie);
            if (ListOfWords.size() == 0)
            {
                cout << "Sorry!\nNo suggestions" << endl;
                cout << "Do you want to enter this word into the memory?(y/n) : ";
                char pp;
                cin >> pp;
                if (pp == 'y' || pp == 'Y')
                {
                    WriteNewWord(trie);
                }
            }
            else
            {
                cout << "Auto complete reply :" << endl;
                for (int i = 0; i < ListOfWords.size(); i++)
                {
                    cout << " \t     " << ListOfWords[i] << endl;
                }
            }
        }
        continue;

        case '2':
            WriteNewWord(trie);
            continue;

        case '3':
            delete trie;
            return 0;
        default:
            cout << "Invalid input!";
            cout << "\nEnter either 1 or 2..";
            continue;
        }
    }
}
