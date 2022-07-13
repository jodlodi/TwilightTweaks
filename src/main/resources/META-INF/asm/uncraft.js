// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'uncrafting': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/item/recipe/UncraftingRecipe',
                'methodName': 'isItemStackAnIngredient',
                'methodDesc': '(Lnet/minecraft/world/item/ItemStack;)Z'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.GETFIELD),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'io/github/jodlodi/twilighttweaks/ASMHooks',
                            'uncraft',
                            '(Ltwilightforest/item/recipe/UncraftingRecipe;Lnet/minecraft/world/item/ItemStack;)Z',
                            false
                            ),
                        new InsnNode(Opcodes.IRETURN)
                        )
                    );
                return methodNode;
            }
        }
    }
}
